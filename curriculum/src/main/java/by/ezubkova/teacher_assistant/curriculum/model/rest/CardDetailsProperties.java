package by.ezubkova.teacher_assistant.curriculum.model.rest;

import by.ezubkova.teacher_assistant.curriculum.model.rest.common.Tag;
import by.ezubkova.teacher_assistant.curriculum.model.rest.common.User;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Data;

@Data
public class CardDetailsProperties {

  private String location;
  private String cardType;
  private LocalTime startTime;
  private LocalTime endTime;

  private Set<Tag> tags = new HashSet<>();
  private Set<User> participants = new HashSet<>();
  private Map<String, String> userDefinedProperties = new HashMap<>();
}
