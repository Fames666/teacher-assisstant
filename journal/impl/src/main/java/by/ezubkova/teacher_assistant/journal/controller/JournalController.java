package by.ezubkova.teacher_assistant.journal.controller;

import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JournalController {

  private final JournalService journalService;

  @GetMapping(params = "requesterId")
  public List<JournalPreviewResponse> getJournalsForRequester(@NotBlank @RequestParam String requesterId) {
    return journalService.findJournalsAvailableForUser(requesterId);
  }

  @GetMapping("/{journalId}/users")
  public List<JournalUserPreviewResponse> getJournalUsersPreviews(@PathVariable Long journalId) {
    return journalService.createJournalUsersPreviews(journalId);
  }
}
