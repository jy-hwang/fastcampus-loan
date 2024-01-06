package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.fastcampus.loan.domain.Application;
import com.fastcampus.loan.dto.ApplicationDTO.Request;
import com.fastcampus.loan.dto.ApplicationDTO.Response;
import com.fastcampus.loan.repository.ApplicationRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

  @InjectMocks
  ApplicationServiceImpl applicationService;

  @Mock
  private ApplicationRepository applicationRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void Should_ReturnREsponseOfNewApplicationEntity_When_RequestCreateApplication() {
    Application entity = Application.builder()
        .name("member kim")
        .cellPhone("010-1111-2222")
        .email("abc@abc.com")
        .hopeAmount(BigDecimal.valueOf(50000000))
        .build();

    Request request = Request.builder()
        .name("member kim")
        .cellPhone("010-1111-2222")
        .email("abc@abc.com")
        .hopeAmount(BigDecimal.valueOf(50000000))
        .build();

    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

    Response actual = applicationService.create(request);

    assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    assertThat(actual.getName()).isSameAs(entity.getName());
  }


  @Test
  void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() {
    Long findId = 1L;
    Application entity = Application.builder()
        .applicationId(1L)
        .build();

    when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = applicationService.get(1L);

    assertThat(actual.getApplicationId()).isSameAs(findId);

  }

  @Test
  void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestUpdateExistApplicationInfo() {
    Long findId = 1L;

    Application entity = Application.builder()
        .applicationId(1L)
        .hopeAmount(BigDecimal.valueOf(50000000))
        .build();

    Request request = Request.builder()
        .hopeAmount(BigDecimal.valueOf(5000000))
        .build();

    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
    when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = applicationService.update(findId, request);

    assertThat(actual.getApplicationId()).isSameAs(findId);
    assertThat(actual.getHopeAmount()).isSameAs(request.getHopeAmount());

  }

  @Test
  void Should_DeletedApplicationEntity_When_RequestDeleteExistApplicationInfo(){
    Long targetId = 1L;

    Application entity = Application.builder()
        .applicationId(1L)
        .build();

    when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
    when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

    applicationService.delete(targetId);

    assertThat(entity.getIsDeleted()).isSameAs(true);
  }

}