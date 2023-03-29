package by.ezubkova.teacher_assistant.curriculum.model.rest;

import static by.ezubkova.teacher_assistant.curriculum.constant.CardDetailsConstraints.DESCRIPTION_MAX_LENGTH;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class CardDetails {

  private Long id;

  @NotBlank
  private String title;

  private String currentStatus;

  @Size(max = DESCRIPTION_MAX_LENGTH)
  private String description;

  private Set<String> externalLinks = new HashSet<>();

  @Valid
  private CardDetailsMetadata metadata;

  @Valid
  private List<CardDetailsComment> comments = new ArrayList<>();

  @Valid
  private List<CardDetailsTodoTask> todoTasks = new ArrayList<>();
}
