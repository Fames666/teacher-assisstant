//package by.ezubkova.teacher_assistant.journal;
//
//import by.ezubkova.teacher_assistant.journal.config.JournalTestDataSupplier;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@SpringBootApplication(scanBasePackages = "by.ezubkova.teacher_assistant")
//@EnableJpaRepositories(basePackages = "by.ezubkova.teacher_assistant")
//@EntityScan(basePackages = "by.ezubkova.teacher_assistant")
//@ComponentScan(basePackages = "by.ezubkova.teacher_assistant")
//public class TeacherAssistantJournalImplApplication {
//
//  public static void main(String[] args) {
//    var context = SpringApplication.run(TeacherAssistantJournalImplApplication.class, args);
//
//    // TODO: for test purposes. Delete later.
//    var journalTestDataSupplier = (JournalTestDataSupplier) context.getBean("JournalTestDataSupplier");
//    journalTestDataSupplier.initDb();
//  }
//}
