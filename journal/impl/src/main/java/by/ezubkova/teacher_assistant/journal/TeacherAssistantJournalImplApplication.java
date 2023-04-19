package by.ezubkova.teacher_assistant.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "by.ezubkova.teacher_assistant")
@EnableJpaRepositories(basePackages = "by.ezubkova.teacher_assistant")
@EntityScan(basePackages = "by.ezubkova.teacher_assistant")
@ComponentScan(basePackages = "by.ezubkova.teacher_assistant")
public class TeacherAssistantJournalImplApplication {

  public static void main(String[] args) {
    SpringApplication.run(TeacherAssistantJournalImplApplication.class, args);
  }
}
