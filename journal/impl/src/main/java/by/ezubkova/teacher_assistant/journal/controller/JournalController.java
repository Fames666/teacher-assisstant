package by.ezubkova.teacher_assistant.journal.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
public class JournalController {

  @GetMapping("/{journalId}")
  public String getJournal(@PathVariable Long journalId) {
    return null;
  }

  @GetMapping(params = "requesterId")
  public void getAllJournals(@NotBlank @RequestParam String requesterId,
                             @PageableDefault(size = 20) Pageable pageable) {

  }
}
