package com.example.redistest.csr;

import com.example.redistest.dto.ReservationRequestDto;
import com.example.redistest.entity.Reservation;
import com.example.redistest.entity.TicketInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;
  private final TicketInfoRepository ticketInfoRepository;

  public Long makeReservation(ReservationRequestDto dto) {
    TicketInfo ticketInfo = ticketInfoRepository.findById(dto.getTicketInfoId()).orElseThrow(
        () -> new IllegalArgumentException("공연정보가 없습니다.")
    );

    if (!ticketInfo.isAvailable()) {
      throw new IllegalArgumentException("현재 예매가 불가능한 공연입니다.");
    }
    if (0 <= ticketInfo.getLeftSeats() - dto.getCount()) {
      int getLeftSeats = ticketInfo.reserveSeats(dto.getCount());

      // 만약 ReservedSeats와 TotalSeats가 같아지면 isAvailable을 false로 변경
      if (getLeftSeats == 0) {
        ticketInfo.setAvailable(false);
      }
      ticketInfoRepository.save(ticketInfo);
      Reservation reservation = new Reservation(dto, dto.getUserId(), ticketInfo);
      reservationRepository.saveAndFlush(reservation);
      return reservation.getId();
    }
    throw new IllegalArgumentException("남은 자리가 없습니다");

  }
}
