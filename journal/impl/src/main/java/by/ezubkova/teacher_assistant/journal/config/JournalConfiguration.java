package by.ezubkova.teacher_assistant.journal.config;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.JOURNAL_MODULE_MSG_SRC;

import by.ezubkova.teacher_assistant.common.factory.MessageSourceFactory;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
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

  @Bean(JOURNAL_MODULE_MSG_SRC)
  public PrefixedMessageSource journalModuleMessageSource(MessageSourceFactory messageSourceFactory) {
    return messageSourceFactory.createMessageSourceForModule(JOURNAL_MODULE_MSG_SRC);
  }
}
