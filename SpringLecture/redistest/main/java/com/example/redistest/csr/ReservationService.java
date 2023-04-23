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
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public Long makeReservation(ReservationRequestDto dto) {
    //    먼저 redis 캐시를 조회
    Integer leftSeats = redisRepository.findByTicketInfoIdFromRedis(dto.getTicketInfoId());
    if (leftSeats == null) {
    //    캐시가 없으면 DB를 통해 남은 좌석 수 차감
      decrementLeftSeatByDB(dto);
    } else {
//      캐시가 있으면 redis에서 남은 좌석 수 차감
      decrementLeftSeatByRedis(dto, leftSeats);
    }
//    예매 발생
    Reservation reservation = new Reservation(dto);
    reservationRepository.saveAndFlush(reservation);
    return reservation.getId();
  }

  //  redis로 좌석 수 변경
  private void decrementLeftSeatByRedis(ReservationRequestDto dto, Integer leftSeats) {
    if (leftSeats - dto.getCount()<0) throw new CustomException(ExceptionType.OUT_OF_TICKET_EXCEPTION);
    redisRepository.changeByTicketInfoIdFromRedis(dto.getTicketInfoId(), dto.getCount());
  }

//  캐시 없으면 DB로 좌석수 변경
  private void decrementLeftSeatByDB(ReservationRequestDto dto) {
    TicketInfo ticketInfo = ticketInfoRepository.findById(dto.getTicketInfoId())
        .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION));
    if (!ticketInfo.isAvailable()) {
      throw new CustomException(ExceptionType.RESERVATION_UNAVAILABLE_EXCEPTION);
    }
    if (0 > ticketInfo.getLeftSeats() - dto.getCount()) {
      throw new CustomException(ExceptionType.OUT_OF_TICKET_EXCEPTION);
    }
    ticketInfo.minusSeats(dto.getCount());
    ticketInfoRepository.save(ticketInfo);
  }


  //  티켓인포 미리 저장
  @Transactional
  public MessageResponseDto saveTicketInfoToRedis(Long ticketInfoId) {
    TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId)
        .orElseThrow(() -> new CustomException(ExceptionType.NOT_FOUND_TICKET_INFO_EXCEPTION));
    redisRepository.saveTicketInfoToRedis(ticketInfo);
    return new MessageResponseDto(HttpStatus.CREATED, "redis에 성공적으로 저장되었습니다.");
  }

//  캐시 삭제
  @Transactional
  public MessageResponseDto deleteByTicketInfoIdFromRedis(Long ticketInfoId) {
    redisRepository.deleteByTicketInfoIdFromRedis(ticketInfoId);
    return new MessageResponseDto(HttpStatus.OK, "redis에서 캐시가 성공적으로 삭제되었습니다.");
  }
}
