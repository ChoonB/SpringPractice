//package com.example.redistest.redis;
//
//import com.example.neticket.reservation.dto.ReservationRequestDto;
//import com.example.neticket.reservation.service.ReservationService;
//import com.example.neticket.user.entity.User;
//import java.util.concurrent.TimeUnit;
//import lombok.RequiredArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ReservationLockFacade {
//
//  private final ReservationService reservationService;
//  private final RedissonClient redissonClient;
//
//  public Long makeReservation(ReservationRequestDto dto, User user){
//    Long ticketInfoId = dto.getTicketInfoId();
//    RLock lock = redissonClient.getLock(String.format("reservation:ticketinfo:%d",ticketInfoId));
//    try{
////      waitTime만큼 기다렸다가 실패하면 exception처리. leaseTime은 lock 거는 최대 시간
//      boolean available = lock.tryLock(60,1, TimeUnit.SECONDS);
//      if (!available){
//        System.out.println("redisson getLock timeout");
//        throw new IllegalArgumentException("redisson getLock timeout");
//      }
//      return reservationService.makeReservations(dto, user, ticketInfoId);
//    } catch (Exception e){
//      throw new RuntimeException(e);
//    } finally {
//      lock.unlock();
//    }
//  }
//
//}
