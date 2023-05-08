package by.ezubkova.teacher_assistant.curriculum.api.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CardConstraints {

  public static final byte CARD_ID_MIN_LENGHT = 6;
  public static final byte CARD_TITLE_MAX_LENGTH = 75;

  @UtilityClass
  public static class CardPerformableActions {

    public static final String CARD_ACTION_CREATED = "card.action.created";
  }
}
