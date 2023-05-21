package by.ezubkova.teacher_assistant.document_management.config;

import static by.ezubkova.teacher_assistant.journal.api.constant.JournalConstants.JOURNAL_MODULE_MSG_SRC;
import static java.util.Locale.forLanguageTag;

import by.ezubkova.teacher_assistant.common.factory.MessageSourceFactory;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ConclusiveProgressReportProperties.class)
@RequiredArgsConstructor
public class DocumentManagementConfiguration {

  private static final String MESSAGES_FILENAME = "messages";
  private static final String DEFAULT_ENCODING = "UTF-8";

  @Value("${application.messages.prefixes.document-management}")
  private final String messagesModulePrefix;
  @Value("${application.messages.default-locale}")
  private final String defaultLocale;

  @Bean("document-management-message-source")
  public MessageSource messageSource(MessageSourceFactory messageSourceFactory) {
    return messageSourceFactory.createMessageSourceForModule("document-management-message-source");
  }
}
