package by.ezubkova.teacher_assistant.library.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import by.ezubkova.teacher_assistant.library.api.service.AttachmentManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping(path = "/lib/api/v1")
@RequiredArgsConstructor
public class AttachmentController {

  private final AttachmentManagerService service;

  @PostMapping(path = "/attachments",
               consumes = MULTIPART_FORM_DATA_VALUE,
               produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<?> saveNewAttachments(MultipartHttpServletRequest multipartRequest) {
    var multiFileMap = multipartRequest.getMultiFileMap();
    var parameterMap = multipartRequest.getParameterMap();

    service.saveNewAttachments(multiFileMap, parameterMap);
    return ResponseEntity.ok().build();
  }
}
