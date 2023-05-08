package by.ezubkova.teacher_assistant.curriculum.api.model;

import static by.ezubkova.teacher_assistant.curriculum.api.constant.CardConstraints.CARD_TITLE_MAX_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CardCreateRequest {

  @NotBlank
  @Size(min = 1, max = CARD_TITLE_MAX_LENGTH)
  private String title;

  @NotBlank
  private String authorId;

  @NotBlank
  private String type;
}
