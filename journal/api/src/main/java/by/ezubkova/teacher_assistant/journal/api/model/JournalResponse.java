package by.ezubkova.teacher_assistant.journal.api.model;

import java.util.List;
import lombok.Data;

@Data
public class JournalResponse {

  private Long id;

  private Byte classNumber;

  private Character classLetter;

  private Short year;

  private List<JournalRowResponse> rows;
}
