package by.ezubkova.teacher_assistant.common.factory;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.*;
import static java.util.Arrays.asList;
import static java.util.Locale.forLanguageTag;

import by.ezubkova.teacher_assistant.common.config.MessageSourceConfigurationProperties;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceFactory {

  private static final String CLASSPATH_PREFIX = "classpath:";
  private static final String MESSAGES_FILENAME = "messages";
  private static final String DEFAULT_ENCODING = "UTF-8";

  private final MessageSourceConfigurationProperties messageSourceProperties;
  private final String[] messagesLocations;

  public MessageSourceFactory(MessageSourceConfigurationProperties messageSourceProperties) {
    this.messageSourceProperties = messageSourceProperties;
    this.messagesLocations = createMessagesLocations();
  }

  public PrefixedMessageSource createMessageSourceForModule(String moduleName) {
    var modulePrefix = messageSourceProperties.prefixes().getOrDefault(moduleName, EMPTY);
    return createMessageSource(modulePrefix);
  }

  private PrefixedMessageSource createMessageSource(String modulePrefix) {
    var properties = new Properties();
    for (var location : messagesLocations) {
      properties.setProperty(location, DEFAULT_ENCODING);
    }

    var defaultLocale = forLanguageTag(messageSourceProperties.defaultLocale());
    var messageSource = new PrefixedMessageSource(modulePrefix);
    messageSource.setBasenames(messagesLocations);
    messageSource.setDefaultLocale(defaultLocale);
    messageSource.setFileEncodings(properties);
    return messageSource;
  }

  private String[] createMessagesLocations() {
    return messageSourceProperties.prefixes().values().stream()
                                  .map(this::createLocationPathFromPrefix)
                                  .toArray(String[]::new);
  }

  private String createLocationPathFromPrefix(String prefix) {
    var builder = new StringBuilder(CLASSPATH_PREFIX);
    var locationSegments = prefix.replace(DASH, UNDERSCORE).split(REGEX_DOT);
    asList(locationSegments).forEach(segment -> builder.append(segment).append(SLASH));
    return builder.append(MESSAGES_FILENAME).toString();
  }
}
