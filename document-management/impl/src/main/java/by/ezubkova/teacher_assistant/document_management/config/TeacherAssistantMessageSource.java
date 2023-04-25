package by.ezubkova.teacher_assistant.document_management.config;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@RequiredArgsConstructor
public class TeacherAssistantMessageSource extends ReloadableResourceBundleMessageSource {

  private final String prefix;

  @Override
  protected String getMessageInternal(String code, Object[] args, Locale locale) {
    if (code != null) {
      return super.getMessageInternal(prefix + code, args, locale);
    }
    return super.getMessageInternal(null, args, locale);
  }
}
