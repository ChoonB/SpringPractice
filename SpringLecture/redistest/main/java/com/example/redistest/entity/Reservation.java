package com.example.redistest.entity;

import com.example.redistest.dto.ReservationRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int count;

  private Long userId;

  @ManyToOne
  @JoinColumn(name = "ticket_info_id", nullable = false)
  private TicketInfo ticketInfo;

  public Reservation(ReservationRequestDto reservationRequestDto, Long userId, TicketInfo ticketInfo) {
    this.count = reservationRequestDto.getCount();
    this.userId = userId;
    this.ticketInfo = ticketInfo;
  }
}
