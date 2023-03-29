package by.ezubkova.teacher_assistant.curriculum.model.rest;

import lombok.Data;

@Data
public class CardDetailsMetadata {

  private String userId;
  private String userName;
  private String lastAction;
  private String lastUpdatedAt;
}
