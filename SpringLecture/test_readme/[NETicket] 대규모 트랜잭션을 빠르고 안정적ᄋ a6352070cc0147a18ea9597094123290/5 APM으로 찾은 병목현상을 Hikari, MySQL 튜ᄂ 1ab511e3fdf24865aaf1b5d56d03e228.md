# 5. APM으로 찾은 병목현상을 Hikari, MySQL 튜닝으로 해결

## 🆘 문제

<aside>
⚠️ PinPoint calltree 메서드별 분석 결과 지나치가 응답시간이 긴 트랜잭션의 경우,
HikariCP의 ‘getConnection()’ 메서드에서 대부분의 응답시간이 소요됨을 확인했습니다.

</aside>

![Untitled](5%20APM%E1%84%8B%E1%85%B3%E1%84%85%E1%85%A9%20%E1%84%8E%E1%85%A1%E1%86%BD%E1%84%8B%E1%85%B3%E1%86%AB%20%E1%84%87%E1%85%A7%E1%86%BC%E1%84%86%E1%85%A9%E1%86%A8%E1%84%92%E1%85%A7%E1%86%AB%E1%84%89%E1%85%A1%E1%86%BC%E1%84%8B%E1%85%B3%E1%86%AF%20Hikari,%20MySQL%20%E1%84%90%E1%85%B2%E1%84%82%201ab511e3fdf24865aaf1b5d56d03e228/Untitled.png)

---

## 👁‍🗨 분석

[GitHub - brettwooldridge/HikariCP: 光 HikariCP・A solid, high-performance, JDBC connection pool at last.](https://github.com/brettwooldridge/HikariCP#obtaining-a-connection)

<aside>
💡 서버에서 DB로 쿼리를 보낼 때 커넥션을 취득하는 과정에서 병목현상이 발생한다고 판단했습니다.

</aside>

---

## 🌟 시도

### 1. **토큰으로 유저정보를 조회할 때 로컬 캐시를 적용.**

```java
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  //  토큰에 있는 email로 유저 정보 조회
  @Override
  @Cacheable(cacheNames = "localCache", key = "#email")
  public UserDetails loadUserByUsername(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new CustomException(ExceptionType.NOT_FOUND_USER_EXCEPTION)
    );
    return new UserDetailsImpl(user, user.getEmail());
  }
}

// -------------------------------------------------------------------
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

@Bean
  public CacheManager localCacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("localCache");
    cacheManager.setCaffeine(Caffeine.newBuilder()
        .expireAfterWrite(1, TimeUnit.HOURS)
        .maximumSize(200));
    return cacheManager;
  }

  @Bean
  public CacheManager redisCacheManager() {
    RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory());
    return builder.build();
  }

  @Primary
  @Bean
  @Override
  public CacheManager cacheManager() {
    return new CompositeCacheManager(localCacheManager(), redisCacheManager());
  }
```

- 프로젝트에서 인증/인가로 사용하고 있는 JWT 토큰의 경우 토큰에 있는 user의 email로 user를 조회하는 메서드가 있는데 토큰이 사용되는 메서드는 모두 이 과정을 거치게 됩니다.
- 로그를 찍어보면 10ms 미만의 간단한 읽기 쿼리이지만 쿼리 숫자를 줄이고 싶어 Local 캐시를 여기에 적용해보았습니다.
- TTL 설정과 더 나은 성능을 위해 Caffeine 라이브러리를 추가해서 캐시 매니저 설정을 해두었습니다.

---

### 2. **Hikari maximumPoolSize와 RDS MySQL max_connections 설정을 조절해 적절한 수의 커넥션 풀을 생성.**

```yaml
datasource:
    hikari:
      maximum-pool-size: 100
```

- Hikari 설정으로 커넥션 풀을 미리 생성하고 MySQL RDS DB에도 거기에 맞는 max_connections 를 설정해보려 했습니다.
- 적절한 수의 커넥션을 커넥션풀에 두고 재활용해, 커넥션이 새로 생성되면서 발생하는 비용과 context switching 비용을 줄이고 getConnection()에 걸리는 시간을 줄이고자 했습니다.
- 적절한 수의 풀 사이즈를 찾기위해 사이즈를 조정해가며 부하테스트를 진행하면서 최적의 커넥션풀 사이즈 100을 찾아냈습니다.

[230502  12번째 테스트 ](https://www.notion.so/230502-12-dce68196b8034f1f9275e2ca2477af5e)

![Untitled](5%20APM%E1%84%8B%E1%85%B3%E1%84%85%E1%85%A9%20%E1%84%8E%E1%85%A1%E1%86%BD%E1%84%8B%E1%85%B3%E1%86%AB%20%E1%84%87%E1%85%A7%E1%86%BC%E1%84%86%E1%85%A9%E1%86%A8%E1%84%92%E1%85%A7%E1%86%AB%E1%84%89%E1%85%A1%E1%86%BC%E1%84%8B%E1%85%B3%E1%86%AF%20Hikari,%20MySQL%20%E1%84%90%E1%85%B2%E1%84%82%201ab511e3fdf24865aaf1b5d56d03e228/Untitled%201.png)

- 오토스케일링으로 최대 3대의 서버 인스턴스가 돌아가기 때문에 프로세스당 커넥션 100에 여유분 40개를 더해 총 340개로 max_connections를 세팅했습니다.

![Untitled](5%20APM%E1%84%8B%E1%85%B3%E1%84%85%E1%85%A9%20%E1%84%8E%E1%85%A1%E1%86%BD%E1%84%8B%E1%85%B3%E1%86%AB%20%E1%84%87%E1%85%A7%E1%86%BC%E1%84%86%E1%85%A9%E1%86%A8%E1%84%92%E1%85%A7%E1%86%AB%E1%84%89%E1%85%A1%E1%86%BC%E1%84%8B%E1%85%B3%E1%86%AF%20Hikari,%20MySQL%20%E1%84%90%E1%85%B2%E1%84%82%201ab511e3fdf24865aaf1b5d56d03e228/Untitled%202.png)

### 3. 이후 서비스 로직 최적화, 세부 세팅 조정

```java
// 2.예매하기
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public Long makeReservation(ReservationRequestDto dto, User user) {
    try {
      Boolean success = redisRepository.decrementLeftSeatInRedis(dto.getTicketInfoId(),
          dto.getCount());
      if (!success) {
        throw new CustomException(ExceptionType.OUT_OF_TICKET_EXCEPTION);
      }
    } catch (Exception e) {
      if (e instanceof CustomException || e instanceof RedisCommandTimeoutException) {
        throw e;
      }
      decrementLeftSeatInDB(dto);
    }
    return reservationRepository.save(new Reservation(dto, user)).getId();

  }

  // 2-1.캐시 없으면 DB로 좌석수 변경
  private void decrementLeftSeatInDB(ReservationRequestDto dto) {
    TicketInfo ticketInfo = ticketInfoRepository.findByIdWithLock(dto.getTicketInfoId())
        .orElseThrow(
            () -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION)
        );
    ticketInfo.minusSeats(dto.getCount());
    ticketInfoRepository.save(ticketInfo);
  }
```

- 키를 확인하지 않고 바로 Redis로 남은 좌석 수를 차감한 뒤 키가 없거나 Redis에 문제가 발생하면 DB를 통해 남은 좌석 수를 차감하도록 로직을 수정했습니다.
- 트랜잭션 하나 당 Redis로 가는 쿼리가 하나 줄어들어 부담이 적어집니다.

---

## 💡 해결

<aside>
🛠 트랜잭션 하나 당 DB와 Redis로 가는 쿼리를 줄이고, 
커넥션 풀을 미리 설정해 커넥션 생성에 따른 오버헤드를 줄여 성능을 개선했습니다.
10K 요청 테스트 진행 시 평균 응답속도 960ms(1223 tps)로 비관적 락 대비 98.06% 개선했습니다. (tps는 15.87배 증가)

</aside>

[230505  15번째 테스트](https://www.notion.so/230505-15-127ace48e8c44662a2f228b6a7774e28)

| 라벨 | 표본 수 | 평균 응답 속도 | 최소값 | 최대값 | 표준편차 | 오류 % | 처리량
(tps) | 수신 KB/초 | 전송 KB/초 | 평균 바이트 수 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 최종 | 10000 | 960ms | 37 | 2392 | 344.0450839164978 | 0.0 | 1223.540927444023 | 401.47436681757006 | 590.2629083567846 | 336.0 |