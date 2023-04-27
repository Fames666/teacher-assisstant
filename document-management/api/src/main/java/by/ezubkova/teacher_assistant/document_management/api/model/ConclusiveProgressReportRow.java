package by.ezubkova.teacher_assistant.document_management.api.model;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConclusiveProgressReportRow {

  private String className;
  private Short studentsAmount;
  private String period;
  private Map<Byte, Integer> markToCountMap;
  private Integer naCount;  // NA -- not attested
  private Integer naWithReasonCount;
  private Float learningLevelPercent;
  private Float knowledgeQualityPercent;
  private Short theoreticalPartHours;
  private Short practicalPartHours;

  public ConclusiveProgressReportRow(String className, Short studentsAmount, String period) {
    this.className = className;
    this.studentsAmount = studentsAmount;
    this.period = period;
  }
}
