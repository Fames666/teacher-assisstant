package by.ezubkova.teacher_assistant.document_management.controller;

import static org.springframework.http.HttpStatus.OK;

import by.ezubkova.teacher_assistant.document_management.service.ConclusiveProgressReportGeneratorService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentManagementController {

  private final ConclusiveProgressReportGeneratorService service;

  @GetMapping(value = "/progress-report",
              params = {"class", "letter", "year"},
              produces = MediaType.APPLICATION_PDF_VALUE)
  public HttpEntity<byte[]> generateConclusiveProgressReport(@RequestParam("class") Byte classNumber,
                                                     @RequestParam("letter") String classLetter,
                                                     @RequestParam Short year)
  throws IOException {
    service.generateConclusiveProgressReport(classNumber, classLetter, year);
    byte[] file = Files.readAllBytes(Paths.get("tables.pdf"));

    var headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Report.pdf\"");
    return new ResponseEntity<>(file, headers, OK);
  }
}
