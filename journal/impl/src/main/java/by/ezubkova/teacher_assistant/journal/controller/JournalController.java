package by.ezubkova.teacher_assistant.journal.controller;

import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalRowResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalService;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JournalController {

  private static final String PARAM_PUT_MARK_ACTION = "action=put-mark";
  private static final String PARAM_TOGGLE_REMARK_ACTION = "action=toggle-remark";

  private final JournalService journalService;

  @GetMapping(params = "requesterId")
  public List<JournalPreviewResponse> getJournalsForRequester(@NotBlank @RequestParam String requesterId) {
    return journalService.findJournalsAvailableForUser(requesterId);
  }

  @GetMapping("/{journalId}/users")
  public List<JournalUserPreviewResponse> getJournalUsersPreviews(@PathVariable Long journalId) {
    return journalService.createJournalUsersPreviews(journalId);
  }

  @GetMapping("/{journalId}")
  public JournalResponse getJournal(@PathVariable Long journalId) {
    return journalService.getJournal(journalId);
  }

  @PutMapping(value = "/{journalId}", params = {PARAM_PUT_MARK_ACTION, "studentId", "date", "mark"})
  public JournalRowResponse putMark(@PathVariable Long journalId,
                                    @RequestParam String studentId,
                                    @RequestParam LocalDate date,
                                    @RequestParam Byte mark) {
    return journalService.putMark(journalId, studentId, date, mark);
  }

  // TODO: add remarkReason
  @PutMapping(value = "/{journalId}", params = {PARAM_TOGGLE_REMARK_ACTION, "studentId", "date"})
  public JournalRowResponse toggleRemark(@PathVariable Long journalId,
                                         @RequestParam String studentId,
                                         @RequestParam LocalDate date) {
    return journalService.toggleRemark(journalId, studentId, date);
  }
}
