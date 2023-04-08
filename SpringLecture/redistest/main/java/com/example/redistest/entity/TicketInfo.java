package com.example.redistest.entity;

import com.example.redistest.dto.TicketInfoRequestDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TicketInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private boolean isAvailable = false;

  @Column(nullable = false)
  private int totalSeats;

  @Column(nullable = false)
  private int leftSeats;

  @Column(nullable = false)
  private LocalDateTime openDate;

  public int reserveSeats(int count) {
    return this.leftSeats -= count;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public TicketInfo(TicketInfoRequestDto ticketInfoRequestDto) {
    this.isAvailable = ticketInfoRequestDto.isAvailable();
    this.totalSeats = ticketInfoRequestDto.getTotalSeats();
    this.leftSeats = ticketInfoRequestDto.getLeftSeats();
    this.openDate = ticketInfoRequestDto.getOpenDate();
  }
}
