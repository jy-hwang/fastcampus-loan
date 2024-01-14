package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BalanceDTO implements Serializable {
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Request {

    private Long applicationId;

    private BigDecimal entryAmount;

  }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class UpdateRequest {

      private Long applicationId;

      private BigDecimal beforeEntryAmount;

      private BigDecimal afterEntryAmount;

    }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Response {
    private Long balanceId;

    private Long applicationId;

    private BigDecimal entryAmount;
  }

}
