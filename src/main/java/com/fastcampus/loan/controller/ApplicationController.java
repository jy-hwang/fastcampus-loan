package com.fastcampus.loan.controller;

import com.fastcampus.loan.dto.ApplicationDTO.AcceptedTermsAndCondition;
import com.fastcampus.loan.dto.ApplicationDTO.Request;
import com.fastcampus.loan.dto.ApplicationDTO.Response;
import com.fastcampus.loan.dto.FileDTO;
import com.fastcampus.loan.dto.ResponseDTO;
import com.fastcampus.loan.service.ApplicationService;
import com.fastcampus.loan.service.FileStorageService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

  private final ApplicationService applicationService;

  private final FileStorageService fileStorageService;

  @PostMapping
  public ResponseDTO<Response> create(@RequestBody Request request) {
    return ok(applicationService.create(request));
  }


  @GetMapping("/{applicationId}")
  public ResponseDTO<Response> get(@PathVariable Long applicationId) {
    return ok(applicationService.get(applicationId));
  }

  @PutMapping("/{applicationId}")
  public ResponseDTO<Response> update(@PathVariable Long applicationId, @RequestBody Request request) {
    return ok(applicationService.update(applicationId, request));

  }


  @DeleteMapping("/{applicationId}")
  public ResponseDTO<Void> delete(@PathVariable Long applicationId) {
    applicationService.delete(applicationId);

    return ok();
  }

  @PostMapping("/{applicationId}/terms")
  public ResponseDTO<Boolean> acceptTerms(@PathVariable Long applicationId, @RequestBody AcceptedTermsAndCondition request) {
    return ok(applicationService.acceptTerms(applicationId, request));
  }

  @PostMapping("/{applicationId}/files")
  public ResponseDTO<Void> upload(@PathVariable Long applicationId, MultipartFile file) {
    fileStorageService.save(applicationId, file);
    return ok();
  }

  @GetMapping("/{applicationId}/files")
  public ResponseEntity<Resource> download(@PathVariable Long applicationId, @RequestParam(value = "fileName") String fileName) {

    Resource file = fileStorageService.load(applicationId, fileName);

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);

  }


  @GetMapping("/{applicationId}/files/infos")
  public ResponseDTO<List<FileDTO>> getFileInfos(@PathVariable Long applicationId) {
    List<FileDTO> fileInfos = fileStorageService.loadAll(applicationId).map(path -> {
      String fileName = path.getFileName().toString();
      return FileDTO.builder()
          .name(fileName)
          .url(MvcUriComponentsBuilder.fromMethodName(ApplicationController.class, "download", applicationId, fileName).build().toString())
          .build();


    }).collect(Collectors.toList());
    return ok(fileInfos);
  }

  @DeleteMapping("/{applicationId}/files")
  public ResponseDTO<Void> deleteAll(@PathVariable Long applicationId) {
    fileStorageService.deleteAll(applicationId);
    return ok();
  }


}
