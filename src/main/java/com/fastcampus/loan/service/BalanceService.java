package com.fastcampus.loan.service;

import com.fastcampus.loan.dto.BalanceDTO.Request;
import com.fastcampus.loan.dto.BalanceDTO.Response;
import com.fastcampus.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {

  Response create(Long applicationId, Request request);

  Response update(Long applicationId, UpdateRequest request);

}
