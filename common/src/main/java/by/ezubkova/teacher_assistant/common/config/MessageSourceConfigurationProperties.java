package by.ezubkova.teacher_assistant.common.config;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.UNDERSCORE;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.resources.messages")
public record MessageSourceConfigurationProperties(
    String defaultLocale,
    Map<String, String> prefixes
) {

  public String getLocaleLanguage() {
    return defaultLocale.split(UNDERSCORE)[0];
  }

  public String getLocaleRegion() {
    return defaultLocale.split(UNDERSCORE)[1];
  }
}
