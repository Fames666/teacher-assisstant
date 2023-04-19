package by.ezubkova.teacher_assistant.journal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
public class JournalController {

  @GetMapping("/{journalId}")
  public String getJournal(@PathVariable Long journalId) {
    return null;
  }
}
