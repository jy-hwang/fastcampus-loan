package com.fastcampus.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO.Request;
import com.fastcampus.loan.dto.CounselDTO.Response;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

  @InjectMocks
  CounselServiceImpl counselService;

  @Mock
  private CounselRepository counselRepository;

  @Spy
  private ModelMapper modelMapper;

  @Test
  void should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
    Counsel entity = Counsel.builder()
        .name("member kim")
        .cellPhone("010-1111-2222")
        .memo("abc@def.g")
        .zipCode("12345")
        .address("서울시 어딘구 모른동")
        .addressDetail("101동 101호")
        .build();

    Request request = Request.builder()
        .name("member kim")
        .cellPhone("010-1111-2222")
        .memo("abc@def.g")
        .zipCode("12345")
        .address("서울시 어딘구 모른동")
        .addressDetail("101동 101호")
        .build();

    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

    Response actual = counselService.create(request);

    assertThat(actual.getName()).isSameAs(entity.getName());
  }

  @Test
  void Should_ReturnResponseOfExistCounselEntity_When_RequestExistCounselId() {
    Long findId = 1L;

    Counsel entity = Counsel.builder().counselId(1L).build();

    when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = counselService.get(findId);

    assertThat(actual.getCounselId()).isSameAs(findId);
  }

  @Test
  void Should_ThrowException_When_RequestNotExistCounselId() {
    Long findId = 2L;
    when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

    Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));
  }


  @Test
  void Should_ReturnUpdatedResponseOfExistCounselEntity_When_RequestUpdateExistCounselInfo() {
    Long findId = 1L;

    Counsel entity = Counsel.builder().counselId(1L).name("member Kim").build();

    Request request = Request.builder()
        .name("member Kang")
        .build();

    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
    when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

    Response actual = counselService.update(findId, request);

    assertThat(actual.getCounselId()).isSameAs(findId);
    assertThat(actual.getName()).isSameAs(request.getName());
    //assertThat(actual.getName()).isSameAs("member Lee");
  }

  @Test
  void Should_DeletedCounselEntity_When_RequestDeleteExistCounselInfo() {
    Long targetId = 1L;

    Counsel entity = Counsel.builder().counselId(1L).build();
    when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
    when(counselRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));
    counselService.delete(targetId);

    assertThat(entity.getIsDeleted()).isSameAs(true);
  }
}
