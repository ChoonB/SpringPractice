package com.example.redistest.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TicketInfoRequestDto {

  private boolean isAvailable = true;
  private int totalSeats;
  private int leftSeats;
  private LocalDateTime openDate;

  public TicketInfoRequestDto(boolean isAvailable, int totalSeats, int leftSeats,
      LocalDateTime openDate) {
    this.isAvailable = isAvailable;
    this.totalSeats = totalSeats;
    this.leftSeats = leftSeats;
    this.openDate = openDate;
  }
}
