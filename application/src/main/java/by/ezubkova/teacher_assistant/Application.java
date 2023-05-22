package by.ezubkova.teacher_assistant;

import by.ezubkova.teacher_assistant.journal.config.JournalTestDataSupplier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "by.ezubkova.teacher_assistant")
@EnableJpaRepositories(basePackages = "by.ezubkova.teacher_assistant")
@EntityScan(basePackages = "by.ezubkova.teacher_assistant")
public class Application {

  public static void main(String[] args) {
    var ctx = SpringApplication.run(Application.class, args);
    ((JournalTestDataSupplier) (ctx.getBean("JournalTestDataSupplier"))).initDb();
  }
}
