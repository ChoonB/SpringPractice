package com.example.redistest.excption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

//  추후 내티켓에 맞게 수정

  BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생하였습니다."),
  TOKEN_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
  LOGIN_FAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "유저 정보가 일치하지 않습니다."),
  ALREADY_EXISTS_EXCEPTION(HttpStatus.CONFLICT, "이미 값이 존재합니다."),
  ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "로그인 후 사용해주세요."),
  ORDER_FINISH_EXCEPTION(HttpStatus.BAD_REQUEST , "주문이 완료되거나 취소된 상품입니다."),
  NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "요청하신 자료를 찾을 수 없습니다."),
  NOT_FOUND_KEY_EXCEPTION(HttpStatus.NOT_FOUND, "요청하신 키를 찾을 수 없습니다."),
  NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "요청하신 유저를 찾을 수 없습니다."),
  ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 존재하는 데이터입니다."),
  OVER_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, "최대 요청 개수를 초과하였습니다."),
  OUT_OF_STOCK_EXCEPTION(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
  ZERO_WORD_EXCEPTION(HttpStatus.BAD_REQUEST, "키워드를 작성해주세요."),
  ONE_WORD_EXCEPTION(HttpStatus.BAD_REQUEST, "두 글자 이상부터 검색이 가능합니다."),
  EMPTY_RESULT_EXCEPTION(HttpStatus.BAD_REQUEST, "조회된 상품이 없습니다.");


  private final HttpStatus httpStatus;
  private final String message;



}
