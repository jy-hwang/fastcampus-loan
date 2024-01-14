package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.AcceptTerms;
import com.fastcampus.loan.domain.Application;
import com.fastcampus.loan.domain.Terms;
import com.fastcampus.loan.dto.ApplicationDTO.AcceptedTermsAndCondition;
import com.fastcampus.loan.dto.ApplicationDTO.Request;
import com.fastcampus.loan.dto.ApplicationDTO.Response;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.AcceptTermsRepository;
import com.fastcampus.loan.repository.ApplicationRepository;
import com.fastcampus.loan.repository.JudgementRepository;
import com.fastcampus.loan.repository.TermsRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final TermsRepository termsRepository;
  private final AcceptTermsRepository acceptTermsRepository;
  private final ModelMapper modelMapper;
  private final JudgementRepository judgementRepository;

  @Override
  public Response create(Request request) {
    Application application = modelMapper.map(request, Application.class);
    application.setAppliedAt(LocalDateTime.now());

    Application applied = applicationRepository.save(application);

    return modelMapper.map(applied, Response.class);
  }

  @Override
  public Response get(Long applicationId) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    return modelMapper.map(application, Response.class);
  }

  @Override
  public Response update(Long applicationId, Request request) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });
    application.setName(request.getName());
    application.setCellPhone(request.getCellPhone());
    application.setEmail(request.getEmail());
    application.setHopeAmount(request.getHopeAmount());

    applicationRepository.save(application);

    return modelMapper.map(application, Response.class);
  }

  @Override
  public void delete(Long applicationId) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });
    application.setIsDeleted(true);

    applicationRepository.save(application);

  }

  @Override
  public boolean acceptTerms(Long applicationId, AcceptedTermsAndCondition request) {
    //1. 대출 신청 정보가 존재해야함.
    applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    //2. 동의할 약관이 하나라도 존재해야함.
    List<Terms> termsList = termsRepository.findAll(Sort.by(Direction.ASC, "termsId"));
    if (termsList.isEmpty()) {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    //3. 게시한 약관의 수와 고객이 동의한 약관의 수가 같은지 비교.
    List<Long> acceptTermsIds = request.getAcceptTermsIds();
    if (termsList.size() != acceptTermsIds.size()) {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    //4. 게시한 약관의 id와 고객이 동의한 약관의 id 가 서로 같은지 비교.
    List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());
    Collections.sort(acceptTermsIds);

    if (!termsIds.containsAll(acceptTermsIds)) {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    for (Long termsId : acceptTermsIds) {
      AcceptTerms accepted = AcceptTerms.builder()
          .termsId(termsId)
          .applicationId(applicationId)
          .build();

      acceptTermsRepository.save(accepted);
    }

    return true;
  }

  @Override
  public Response contract(Long applicationId) {
    // 신청 정보 있는지
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    // 심사 정보 있는지
    judgementRepository.findByApplicationId(applicationId).orElseThrow(() -> {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    });

    // 승인 금액 > 0
    if (application.getApprovalAmount() == null || application.getApprovalAmount().compareTo(BigDecimal.ZERO) == 0) {
      throw new BaseException(ResultType.SYSTEM_ERROR);
    }

    // 계약 체결
    application.setContractedAt(LocalDateTime.now());
    applicationRepository.save(application);
    return null;
  }
}
