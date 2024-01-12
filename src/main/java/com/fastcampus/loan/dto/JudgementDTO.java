package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class JudgementDTO implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Request {

    private Long applicationId;

    private String name;

    private BigDecimal approvalAmount;

  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  public static class Response {

    private Long judgementId;

    private Long applicationId;

    private String name;

    private BigDecimal approvalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  }
}
