package by.ezubkova.teacher_assistant.journal.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class JournalRowResponse {

  private Long journalId;

  private StudentData student;

  private String decorationType;

  private BigDecimal averageMark;

  private List<JournalCellResponse> cells = new ArrayList<>();
}
