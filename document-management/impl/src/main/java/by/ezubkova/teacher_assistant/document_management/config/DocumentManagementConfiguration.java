package by.ezubkova.teacher_assistant.document_management.config;

import static java.util.Locale.forLanguageTag;

import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DocumentManagementConfiguration {

  private static final String MESSAGES_FILENAME = "messages";
  private static final String DEFAULT_ENCODING = "UTF-32";

  @Value("${application.messages.prefixes.document-management}")
  private final String messagesModulePrefix;
  @Value("${application.messages.default-locale}")
  private final String defaultLocale;

  @Bean
  public MessageSource messageSource() {
    var properties = new Properties();
    properties.setProperty(MESSAGES_FILENAME, DEFAULT_ENCODING);

    var messageSource = new TeacherAssistantMessageSource(messagesModulePrefix);
    messageSource.setBasename(MESSAGES_FILENAME);
    messageSource.setDefaultLocale(forLanguageTag(defaultLocale));
    messageSource.setFileEncodings(properties);
    return messageSource;
  }
}
