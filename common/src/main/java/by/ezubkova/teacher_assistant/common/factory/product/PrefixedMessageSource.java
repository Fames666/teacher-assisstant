package by.ezubkova.teacher_assistant.common.factory.product;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.DOT;
import static java.lang.String.format;
import static java.util.Locale.getDefault;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@RequiredArgsConstructor
public class PrefixedMessageSource extends ReloadableResourceBundleMessageSource {

  private final String prefix;

  public String getMessage(String code) {
    return super.getMessage(code, new Object[0], getDefault());
  }

  public String getMessage(String code, Object... args) {
    return super.getMessage(code, args, getDefault());
  }

  @Override
  protected String getMessageInternal(String code, Object[] args, Locale locale) {
    code = code != null ? format("%s%s%s", prefix, DOT, code) : null;
    return super.getMessageInternal(code, args, locale);
  }
}
