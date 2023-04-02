package by.ezubkova.teacher_assistant.library.util;

import java.util.Collection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtil {

  public static class CollectionUtil {

    public static boolean nonEmpty(Collection<?> collection) {
      return collection != null && !collection.isEmpty();
    }
  }
}
