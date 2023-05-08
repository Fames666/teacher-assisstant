package by.ezubkova.teacher_assistant.common.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

  /**
   * @param defaultValue value to return, if each {@code object} is {@code null}.
   * @param objects      values to inspect for nullability.
   */
  @SafeVarargs
  public static <T> T firstNonNull(T defaultValue, T... objects) {
    for (T object : objects) {
      if (object != null) {
        return object;
      }
    }
    return defaultValue;
  }

  public static <ITEM, MAPPED_ITEM, COLLECTION extends Collection<ITEM>>
  List<MAPPED_ITEM> findAll(COLLECTION source,
                            Function<ITEM, MAPPED_ITEM> mapper,
                            Predicate<MAPPED_ITEM> filter) {
    return source.stream().map(mapper).filter(filter).toList();
  }
}
