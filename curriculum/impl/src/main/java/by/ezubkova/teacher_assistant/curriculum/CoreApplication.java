package by.ezubkova.teacher_assistant.curriculum;

import by.ezubkova.teacher_assistant.curriculum.jpa.repository.CardRepository;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "by.ezubkova.teacher_assistant")
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, CardRepository.class})
@EntityScan(basePackages = {
    "by.ezubkova.teacher_assistant.curriculum.jpa.model",
    "by.ezubkova.teacher_assistant.user_management.jpa.model"
})
@ComponentScan(basePackages = {
    "by.ezubkova.teacher_assistant.curriculum",
    "by.ezubkova.teacher_assistant.user_management"
})
public class CoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(CoreApplication.class, args);
  }
}
