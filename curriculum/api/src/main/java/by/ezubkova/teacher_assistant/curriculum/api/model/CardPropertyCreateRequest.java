package by.ezubkova.teacher_assistant.curriculum.api.model;

import lombok.Data;

@Data
public class CardPropertyCreateRequest {

  private Long id;

  private String name;

  private String valueType;

  private byte[] value;
}
