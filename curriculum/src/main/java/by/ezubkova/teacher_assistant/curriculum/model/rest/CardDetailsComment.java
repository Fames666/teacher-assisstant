package by.ezubkova.teacher_assistant.curriculum.model.rest;

import static by.ezubkova.teacher_assistant.curriculum.constant.CardDetailsConstraints.COMMENT_MAX_LENGTH;
import static by.ezubkova.teacher_assistant.curriculum.constant.CardDetailsConstraints.COMMENT_MIN_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardDetailsComment {

  private Long id;

  @NotBlank
  private String userId;

  @NotBlank
  @Size(min = COMMENT_MIN_LENGTH, max = COMMENT_MAX_LENGTH)
  private String text;
}
