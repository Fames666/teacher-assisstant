package by.ezubkova.teacher_assistant.journal.error;

import by.ezubkova.teacher_assistant.common.error.ApplicationBaseException;

public class JournalNotFoundException extends ApplicationBaseException {

  public JournalNotFoundException(String message) {
    super(message);
  }
}
