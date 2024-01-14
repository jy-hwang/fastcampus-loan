package com.fastcampus.loan.service;

import com.fastcampus.loan.dto.EntryDTO.Request;
import com.fastcampus.loan.dto.EntryDTO.Response;
import com.fastcampus.loan.dto.EntryDTO.UpdateResponse;
import org.springframework.http.RequestEntity;

public interface EntryService {

  Response create(Long applicationId, Request request);

  Response get(Long applicationId);

  UpdateResponse update(Long entryId, Request request);

  void delete(Long entryId);

}
