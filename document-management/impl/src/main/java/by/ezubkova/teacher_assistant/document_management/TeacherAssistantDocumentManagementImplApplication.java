package by.ezubkova.teacher_assistant.document_management;

import by.ezubkova.teacher_assistant.document_management.service.ConclusiveProgressReportGeneratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeacherAssistantDocumentManagementImplApplication {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(TeacherAssistantDocumentManagementImplApplication.class, args);
    var svc = (ConclusiveProgressReportGeneratorService) ctx.getBean("conclusiveProgressReportGeneratorService");
    svc.generateConclusiveProgressReport();
  }
}
