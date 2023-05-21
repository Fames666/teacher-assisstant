package by.ezubkova.teacher_assistant.document_management.controller;

import by.ezubkova.teacher_assistant.document_management.service.ConclusiveProgressReportGeneratorService;
import lombok.RequiredArgsConstructor;
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

  @GetMapping(value = "/progress-report", params = {"class", "letter", "year"})
  public void generateConclusiveProgressReport(@RequestParam("class") Byte classNumber,
                                               @RequestParam("letter") String classLetter,
                                               @RequestParam Short year) {
    service.generateConclusiveProgressReport(classNumber, classLetter, year);
  }
}
