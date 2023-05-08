package by.ezubkova.teacher_assistant.common.error;

import static java.lang.String.format;

public class UserNotFoundException extends ApplicationBaseException {

  private static final String EXCEPTION_TEXT = "No user found with ID = %s";

  public UserNotFoundException(String userId) {
    super(format(EXCEPTION_TEXT, userId));
  }
}
