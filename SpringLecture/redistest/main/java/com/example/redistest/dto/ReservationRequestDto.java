package com.example.redistest.dto;

import lombok.Getter;

@Getter
public class ReservationRequestDto {

  private Long ticketInfoId;
  private int count;
  private Long userId;

  public ReservationRequestDto(Long ticketInfoId, Long userId, int count) {
    this.ticketInfoId = ticketInfoId;
    this.userId = userId;
    this.count = count;
  }
}
