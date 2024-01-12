package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ApplicationDTO implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Request {

    private String name;

    private String cellPhone;

    private String email;

    private BigDecimal hopeAmount;

  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class Response {

    private Long applicationId;

    private String name;

    private String cellPhone;

    private String email;

    private BigDecimal hopeAmount;

    private LocalDateTime appliedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  public static class AcceptedTermsAndCondition {

    private List<Long> acceptTermsIds;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  @Builder
  public static class GrantAmount {

    private Long applicationId;

    private BigDecimal approvalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }
}
