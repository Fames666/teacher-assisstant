package by.ezubkova.teacher_assistant.common.util;

import static java.util.Arrays.asList;

import java.util.ArrayList;
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
  public static <OBJECT> OBJECT firstNonNull(OBJECT defaultValue, OBJECT... objects) {
    for (OBJECT object : objects) {
      if (object != null) {
        return object;
      }
    }
    return defaultValue;
  }

  public static <ITEM, COLLECTION extends Collection<ITEM>> ITEM first(COLLECTION source) {
    return source != null && !source.isEmpty() ? source.iterator().next() : null;
  }

  public static <ITEM, MAPPED_ITEM, COLLECTION extends Collection<ITEM>>
  List<MAPPED_ITEM> findAll(COLLECTION source,
                            Function<ITEM, MAPPED_ITEM> mapper,
                            Predicate<MAPPED_ITEM> filter) {
    return source.stream().map(mapper).filter(filter).toList();
  }

  public static <ITEM, COLLECTION extends Collection<ITEM>>
  List<ITEM> filter(COLLECTION source, Predicate<ITEM> filter) {
    return source.stream().filter(filter).toList();
  }

  public static <ITEM, MAPPED_ITEM, COLLECTION extends Collection<ITEM>>
  List<MAPPED_ITEM> map(COLLECTION source, Function<ITEM, MAPPED_ITEM> mapper) {
    return source.stream().map(mapper).toList();
  }

  @SafeVarargs
  public static <ITEM> List<ITEM> newArrayList(ITEM... items) {
    return new ArrayList<>(asList(items));
  }

  public static <COLLECTION extends Collection<?>> boolean isEmpty(COLLECTION source) {
    return source == null || source.isEmpty();
  }
}
