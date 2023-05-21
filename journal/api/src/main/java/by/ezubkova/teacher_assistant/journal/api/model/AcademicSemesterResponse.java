package by.ezubkova.teacher_assistant.journal.api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AcademicSemesterResponse {

  private Short year;

  private Byte semester;

  private LocalDate startDate;

  private LocalDate endDate;

  private List<AcademicSemesterPartition> partitions;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AcademicSemesterPartition {

    private String month;

    private Map<Integer, String> daysOfWeek;
  }
}
