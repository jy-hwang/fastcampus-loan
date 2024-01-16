package com.fastcampus.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
