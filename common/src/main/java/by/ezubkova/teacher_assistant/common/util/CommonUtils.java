package by.ezubkova.teacher_assistant.common.util;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.EMPTY;
import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.SPACE;
import static java.lang.String.format;

import by.ezubkova.teacher_assistant.common.util.structure.FullNameAware;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

  public static boolean isTrue(Boolean value) {
    return value != null && value;
  }

  public static String buildFullName(FullNameAware source) {
    if (source == null) {
      return EMPTY;
    }
    var builder = new StringBuilder(source.getSecondName())
        .append(SPACE).append(source.getFirstName());
    if (source.getThirdName() != null) {
      builder.append(SPACE).append(source.getThirdName());
    }
    return builder.toString();
  }

  public static String capitalizeFirstLetter(String source) {
    if (source == null || source.isBlank()) {
      return source;
    }
    if (source.length() == 1) {
      return source.toUpperCase();
    }
    var firstLetter = source.substring(0, 1).toUpperCase();
    return format("%s%s", firstLetter, source.substring(1));
  }
}
