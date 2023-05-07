package by.ezubkova.teacher_assistant.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ObjectUtils {

  public static boolean isTrue(Boolean value) {
    return value != null && value;
  }
}
