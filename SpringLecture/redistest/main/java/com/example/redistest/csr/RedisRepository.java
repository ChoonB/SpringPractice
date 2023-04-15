package com.example.redistest.csr;

import com.example.redistest.entity.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {
  private final RedisTemplate<String, Integer> redisTemplate;

  public void saveTicketInfoToRedis(TicketInfo ticketInfo) {
    String key = "ls" + ticketInfo.getId();
    redisTemplate.opsForValue().set(key, ticketInfo.getLeftSeats());
  }

  public void saveTicketInfoFromRedis(){

  }

  public Integer findByTicketInfoIdFromRedis(Long ticketInfoId){
    String key = "ls" + ticketInfoId;
    return redisTemplate.opsForValue().get(key);
  }

  public void changeByTicketInfoIdFromRedis(Long ticketInfoId, int count){
    String key = "ls" + ticketInfoId;
    redisTemplate.opsForValue().decrement(key, count);
  }

  public void deleteByTicketInfoIdFromRedis(Long ticketInfoId){
    String key = "ls" + ticketInfoId;
    redisTemplate.delete(key);
  }

}
