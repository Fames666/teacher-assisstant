package by.ezubkova.teacher_assistant.curriculum.model.rest.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {

  @EqualsAndHashCode.Include
  private String uuid;
  private String firstName;
  private String secondName;
  private String thirdName;
  private byte[] avatar;
}
