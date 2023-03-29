package by.ezubkova.teacher_assistant.curriculum.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CardDetailsConstraints {

  public static final short DESCRIPTION_MAX_LENGTH = 5000;

  public static final short COMMENT_MIN_LENGTH = 1;
  public static final short COMMENT_MAX_LENGTH = 1000;

  public static final short TODO_MIN_LENGTH = 1;
  public static final short TODO_MAX_LENGTH = 1000;
}
