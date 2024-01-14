package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class EntryDTO implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Request {

    private BigDecimal entryAmount;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Response {

    private Long entryId;

    private Long applicationId;

    private BigDecimal entryAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class UpdateResponse {

    private Long entryId;

    private Long applicationId;

    private BigDecimal beforeEntryAmount;

    private BigDecimal afterEntryAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }


}
