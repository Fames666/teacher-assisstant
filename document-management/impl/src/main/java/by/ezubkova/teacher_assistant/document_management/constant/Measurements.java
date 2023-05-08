package by.ezubkova.teacher_assistant.document_management.constant;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Measurements {

  public static final int CONCLUSIVE_PROGRESS_REPORT_MARKS_COLUMNS = 10;
  public static final int CONCLUSIVE_PROGRESS_REPORT_TOTAL_COLUMNS = 9 + CONCLUSIVE_PROGRESS_REPORT_MARKS_COLUMNS;

  public static final BigDecimal POINTS_IN_CM = valueOf(28.3464566929);

  public static final int DEFAULT_SPAN = 1;

  public static final short VERTICAL = 90;
  public static final short HORIZONTAL = 0;

  public static float cmToPoints(float centimeters) {
    return valueOf(centimeters).multiply(POINTS_IN_CM).floatValue();
  }
}
