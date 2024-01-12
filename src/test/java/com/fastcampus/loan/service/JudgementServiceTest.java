package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.loan.domain.Application;
import com.fastcampus.loan.domain.Judgement;
import com.fastcampus.loan.dto.ApplicationDTO.GrantAmount;
import com.fastcampus.loan.dto.JudgementDTO.Request;
import com.fastcampus.loan.dto.JudgementDTO.Response;
import com.fastcampus.loan.repository.ApplicationRepository;
import com.fastcampus.loan.repository.JudgementRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class JudgementServiceTest {

  @InjectMocks
  private JudgementServiceImpl judgementService;

  @Mock
  private JudgementRepository judgementRepository;

  @Mock
  private ApplicationRepository applicationRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnResponseOfNewJudgementEntity_When_RequestNewJudgement() {
    Judgement judgement = Judgement.builder()
        .applicationId(1L)
        .name("Member Kim")
        .approvalAmount(BigDecimal.valueOf(5000000))
        .build();

    Request request = Request.builder()
        .applicationId(1L)
        .name("Member Kim")
        .approvalAmount(BigDecimal.valueOf(5000000))
        .build();

    //mock
    //application find
    when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));

    //judgement save
    when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(judgement);

    Response actual = judgementService.create(request);
    assertThat(actual.getName()).isSameAs(judgement.getName());
    assertThat(actual.getApplicationId()).isSameAs(judgement.getApplicationId());
    assertThat(actual.getApprovalAmount()).isSameAs(judgement.getApprovalAmount());
  }


  @Test
  void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistJudgementId() {
    Judgement entity = Judgement.builder()
        .judgementId(1L)
        .build();
    when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
    Response actual = judgementService.get(1L);
    assertThat(actual.getJudgementId()).isSameAs(1L);

  }


  @Test
  void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistApplicationId() {
    Long findId = 1L;

    Application applicationEntity = Application.builder()
        .applicationId(1L)
        .build();

    Judgement entity = Judgement.builder()
        .judgementId(1L)
        .build();

    when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(applicationEntity));
    when(judgementRepository.findByApplicationId(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = judgementService.getJudgementOfApplication(findId);

    assertThat(actual.getJudgementId()).isSameAs(1L);
  }


  @Test
  void Should_ReturnUpdatedResponseOfExistJudgementEntity_When_RequestUpdateExistJudgementInfo() {
    Judgement entity = Judgement.builder()
        .judgementId(1L)
        .name(("Member Kim"))
        .approvalAmount(BigDecimal.valueOf(5000000))
        .build();

    Request request = Request.builder()
        .name("Member Lee")
        .approvalAmount(BigDecimal.valueOf(10000000))
        .build();

    when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
    when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(null);

    Response actual = judgementService.update(1L, request);

    assertThat(actual.getJudgementId()).isSameAs((1L));
    assertThat(actual.getName()).isSameAs(request.getName());
    ;
    assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
  }

  @Test
  void Should_DeletedJudgementEntity_When_RequestDeleteExistJudgementInfo() {
    Judgement entity = Judgement.builder()
        .judgementId(1L)
        .build();

    when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
    when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(entity);

    judgementService.delete(1L);

    assertThat(entity.getIsDeleted()).isTrue();
  }

  @Test
  void Should_ReturnUpdateResponseOfExistApplicationEntity_When_RequestGrantApprovalAmountOfJudgementInfo() {

    Judgement judgementEntity = Judgement.builder()
        .name("Member Hwang")
        .judgementId(1L)
        .approvalAmount(BigDecimal.valueOf(5000000))
        .build();

    Application applicationEntity = Application.builder()
        .applicationId(1L)
        .approvalAmount(BigDecimal.valueOf(5000000))
        .build();

    when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(judgementEntity));
    when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(applicationEntity));
    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(applicationEntity);
    GrantAmount actual = judgementService.grant(1L);

    assertThat(actual.getApplicationId()).isSameAs(1L);
    assertThat(actual.getApprovalAmount()).isSameAs(judgementEntity.getApprovalAmount());

  }

}
