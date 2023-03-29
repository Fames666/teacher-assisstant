package by.ezubkova.teacher_assistant.curriculum.model.rest;

import static by.ezubkova.teacher_assistant.curriculum.constant.CardDetailsConstraints.TODO_MAX_LENGTH;
import static by.ezubkova.teacher_assistant.curriculum.constant.CardDetailsConstraints.TODO_MIN_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardDetailsTodoTask {

  private Long id;

  @NotNull
  @PositiveOrZero
  private Long cardId;

  @NotNull
  private Boolean completed;

  @NotBlank
  @Size(min = TODO_MIN_LENGTH, max = TODO_MAX_LENGTH)
  private String text;
}
