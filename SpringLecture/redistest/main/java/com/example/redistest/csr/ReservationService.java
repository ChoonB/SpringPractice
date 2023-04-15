package com.example.redistest.csr;

import com.example.redistest.dto.MessageResponseDto;
import com.example.redistest.dto.ReservationRequestDto;
import com.example.redistest.entity.Reservation;
import com.example.redistest.entity.TicketInfo;
import com.example.redistest.excption.CustomException;
import com.example.redistest.excption.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final TicketInfoRepository ticketInfoRepository;
  private final RedisRepository redisRepository;

//   예매하기
  @Transactional
  public Long makeReservation(ReservationRequestDto dto) {
    checkAndMinusLeftSeatByRedis(dto);
    Reservation reservation = new Reservation(dto);
    reservationRepository.saveAndFlush(reservation);
    return reservation.getId();
  }

//// 좌석 수 변경
//  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
//  public TicketInfo checkAndMinusLeftSeat(ReservationRequestDto dto) {
//    TicketInfo ticketInfo = ticketInfoRepository.findById(dto.getTicketInfoId())
//        .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION));
//    if (!ticketInfo.isAvailable()) {
//      throw new CustomException(ExceptionType.RESERVATION_UNAVAILABLE_EXCEPTION);
//    }
//    if (0 > ticketInfo.getLeftSeats() - dto.getCount()) {
//      throw new CustomException(ExceptionType.OUT_OF_TICKET_EXCEPTION);
//    }
//    ticketInfo.minusSeats(dto.getCount());
//    return ticketInfoRepository.save(ticketInfo);
//  }

//  redis로 좌석 수 변경
  @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
  public void checkAndMinusLeftSeatByRedis(ReservationRequestDto dto) {
    int leftSeats = redisRepository.findByTicketInfoIdFromRedis(dto.getTicketInfoId());
    if (leftSeats - dto.getCount()<0) throw new CustomException(ExceptionType.OUT_OF_TICKET_EXCEPTION);
    redisRepository.changeByTicketInfoIdFromRedis(dto.getTicketInfoId(), dto.getCount());
  }

//  티켓인포 미리 저장
  public MessageResponseDto saveTicketInfoToRedis(Long ticketInfoId) {
    TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId)
        .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION));
    redisRepository.saveTicketInfoToRedis(ticketInfo);
    return new MessageResponseDto(HttpStatus.OK, "redis에 성공적으로 저장되었습니다.");
  }
}
