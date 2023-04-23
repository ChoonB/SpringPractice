package com.example.redistest.csr;

import com.example.redistest.dto.MessageResponseDto;
import com.example.redistest.dto.ReservationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

//  예약하기
  @PostMapping("/reservations")
  public Long makeReservation(@RequestBody ReservationRequestDto dto) {
    return reservationService.makeReservation(dto);
  }

//  캐시 저장
  @PostMapping("/cache/{ticketInfoId}")
  public MessageResponseDto saveTicketInfoToRedis(@PathVariable Long ticketInfoId) {
    return reservationService.saveTicketInfoToRedis(ticketInfoId);
  }

//  캐시삭제
  @DeleteMapping("/cache/{ticketInfoId}")
  public MessageResponseDto deleteByTicketInfoIdFromRedis(@PathVariable Long ticketInfoId) {
    return reservationService.deleteByTicketInfoIdFromRedis(ticketInfoId);
  }

}
