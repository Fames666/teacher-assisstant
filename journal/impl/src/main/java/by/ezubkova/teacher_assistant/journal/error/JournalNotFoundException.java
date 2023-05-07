package by.ezubkova.teacher_assistant.journal.error;

import static java.lang.String.format;

import by.ezubkova.teacher_assistant.common.error.ApplicationBaseException;

public class JournalNotFoundException extends ApplicationBaseException {

  public JournalNotFoundException(String message) {
    super(message);
  }

  public static JournalNotFoundException byId(Long id) {
    return new JournalNotFoundException(format("Couldn't find Journal with ID = %d", id));
  }
}
