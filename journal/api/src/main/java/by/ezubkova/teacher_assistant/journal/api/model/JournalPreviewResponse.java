package by.ezubkova.teacher_assistant.journal.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalPreviewResponse {

  private Long id;

  private String journalFullName;
}
