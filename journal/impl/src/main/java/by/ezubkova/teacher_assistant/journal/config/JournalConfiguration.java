package by.ezubkova.teacher_assistant.journal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

@Configuration
public class JournalConfiguration {

  @Bean
  public PageableArgumentResolver pageableArgumentResolver() {
    return new PageableHandlerMethodArgumentResolver();
  }
}
