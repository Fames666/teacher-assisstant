package by.ezubkova.teacher_assistant.journal.api.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class JournalCellResponse {

  private LocalDate date;

  private String highlightType;

  private Byte mark;

  private Boolean uncertain;

  private String uncertaintyReason;
}
