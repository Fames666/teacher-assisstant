package by.ezubkova.teacher_assistant.journal.api.model;

import by.ezubkova.teacher_assistant.journal.api.constant.StudentPreviewMarkerType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalUserPreviewResponse {

  private String id;
  private String fullName;
  private String lastOnline;

  @JsonUnwrapped
  private StudentPreviewMarker marker;

  public record StudentPreviewMarker(
      StudentPreviewMarkerType marker,
      Integer remarksAmount
  ) { }
}
