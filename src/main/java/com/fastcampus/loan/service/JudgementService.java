package com.fastcampus.loan.service;

import com.fastcampus.loan.dto.ApplicationDTO.GrantAmount;
import com.fastcampus.loan.dto.JudgementDTO.Request;
import com.fastcampus.loan.dto.JudgementDTO.Response;

public interface JudgementService {

  Response create(Request request);

  Response get(Long judgementId);

  Response getJudgementOfApplication(Long applicationId);

  Response update(Long judgementId, Request request);

  void delete(Long judgementId);

  GrantAmount grant(Long judgementId);

}
