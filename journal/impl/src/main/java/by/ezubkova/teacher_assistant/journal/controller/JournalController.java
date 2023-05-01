package by.ezubkova.teacher_assistant.journal.controller;

import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalCrudService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor
public class JournalController {

  private final JournalCrudService journalCrudService;

  @GetMapping("/{journalId}")
  public String getJournal(@PathVariable Long journalId) {
    return null;
  }

  @GetMapping(params = "requesterId")
  public List<JournalResponse> getAllJournals(@NotBlank @RequestParam String requesterId) {
    return journalCrudService.readJournalsAvailableForUser(requesterId);
  }
}
