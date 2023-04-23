package com.example.redistest.csr;

import com.example.redistest.entity.TicketInfo;
import com.example.redistest.excption.CustomException;
import com.example.redistest.excption.ExceptionType;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RedisRepository {
  private final RedisTemplate<String, Integer> redisTemplate;
  private final TicketInfoRepository ticketInfoRepository;

//  키를 ls1 ls2 이런식으로 저장
//  TTL 향후 적용!!
  public void saveTicketInfoToRedis(TicketInfo ticketInfo) {
    String key = "ls" + ticketInfo.getId();
    redisTemplate.opsForValue().set(key, ticketInfo.getLeftSeats());
  }

//  매분 캐시 변경분을 db에 저장
  @Scheduled(cron = "0 * * * * *")
  public void saveTicketInfoFromRedis(){
    Set<String> keys = redisTemplate.keys("ls*");
    if (keys.isEmpty()) return;
    for (String key : keys) {
      Integer leftSeatsInRedis = redisTemplate.opsForValue().get(key);
      Long ticketInfoId = Long.parseLong(key.substring(2));
      TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId).orElseThrow(
          () -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION));
//      예외처리?
      ticketInfo.setLeftSeats(leftSeatsInRedis);
      ticketInfoRepository.save(ticketInfo);
    }

  }

//  key로 조회
  public Integer findByTicketInfoIdFromRedis(Long ticketInfoId){
    String key = "ls" + ticketInfoId;
    return redisTemplate.opsForValue().get(key);
  }

//  값 변경. count만큼 남은좌석수 깎기
  public void changeByTicketInfoIdFromRedis(Long ticketInfoId, int count){
    String key = "ls" + ticketInfoId;
    redisTemplate.opsForValue().decrement(key, count);
  }

//  키 삭제
  public void deleteByTicketInfoIdFromRedis(Long ticketInfoId){
    String key = "ls" + ticketInfoId;
    saveTicketInfoFromRedis();
    redisTemplate.delete(key);
  }

}
