package com.fastcampus.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceDTO implements Serializable {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class CreateRequest {

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
    public static class RepaymentRequest {
        private RepaymentType type;
        private BigDecimal repaymentAmount;

        public enum RepaymentType {
            ADD, REMOVE
        }

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long balanceId;

        private Long applicationId;

        private BigDecimal balance;
    }

}
