package com.fastcampus.loan.controller;

import com.fastcampus.loan.dto.ApplicationDTO.GrantAmount;
import com.fastcampus.loan.dto.JudgementDTO.Request;
import com.fastcampus.loan.dto.JudgementDTO.Response;
import com.fastcampus.loan.dto.ResponseDTO;
import com.fastcampus.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {

  private final JudgementService judgementService;

  @PostMapping
  public ResponseDTO<Response> create(@RequestBody Request request) {
    return ok(judgementService.create(request));
  }

  @GetMapping("/{judgementId}")
  public ResponseDTO<Response> get(@PathVariable Long judgementId) {
    return ok(judgementService.get(judgementId));
  }


  @GetMapping("/applications/{applicationId}")
  public ResponseDTO<Response> getJudgementOfApplication(@PathVariable Long applicationId) {
    return ok(judgementService.getJudgementOfApplication(applicationId));
  }

  @PutMapping("/{judgementId}")
  public ResponseDTO<Response> update(@PathVariable Long judgementId, @RequestBody Request request) {
    return ok(judgementService.update(judgementId, request));
  }

  @DeleteMapping("/{judgementId}")
  public ResponseDTO<Void> delete(@PathVariable Long judgementId) {
    judgementService.delete(judgementId);
    return ok();
  }

  @PatchMapping("/{judgementId}/grants")
  public ResponseDTO<GrantAmount> grant(@PathVariable Long judgementId) {
    return ok(judgementService.grant(judgementId));

  }

}
