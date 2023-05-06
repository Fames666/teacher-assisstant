package by.ezubkova.teacher_assistant.common.factory;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.EMPTY;
import static java.util.Locale.forLanguageTag;

import by.ezubkova.teacher_assistant.common.config.MessageSourceConfigurationProperties;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceFactory {

  // TODO: create common path for resources
  private static final String MESSAGES_FILENAME = "classpath:messages";
  private static final String DEFAULT_ENCODING = "UTF-8";

  private final MessageSourceConfigurationProperties messageSourceProperties;

  public PrefixedMessageSource createMessageSourceForModule(String moduleName) {
    var modulePrefix = messageSourceProperties.prefixes().getOrDefault(moduleName, EMPTY);
    return createMessageSource(modulePrefix);
  }

  private PrefixedMessageSource createMessageSource(String modulePrefix) {
    var properties = new Properties();
    properties.setProperty(MESSAGES_FILENAME, DEFAULT_ENCODING);

    var defaultLocale = forLanguageTag(messageSourceProperties.defaultLocale());
    var messageSource = new PrefixedMessageSource(modulePrefix);
    messageSource.setBasename(MESSAGES_FILENAME);
    messageSource.setDefaultLocale(defaultLocale);
    messageSource.setFileEncodings(properties);
    return messageSource;
  }
}
