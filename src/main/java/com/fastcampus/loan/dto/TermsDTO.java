package com.fastcampus.loan.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class TermsDTO<T> implements Serializable {

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  @Setter
  public static class Request {

    private String name;

    private String termsDetailUrl;


  }


  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Builder
  @Setter
  public static class Response {

    private String termsId;

    private String name;

    private String termsDetailUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
  }
}
