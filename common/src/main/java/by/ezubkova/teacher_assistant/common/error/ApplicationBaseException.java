package by.ezubkova.teacher_assistant.common.error;

public abstract class ApplicationBaseException extends RuntimeException {

  public ApplicationBaseException(String message) {
    super(message);
  }
}
