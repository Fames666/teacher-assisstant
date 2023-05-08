package by.ezubkova.teacher_assistant.curriculum.api.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CardResponse {

  private String id;  // format 000001

  private String title;

  private Author author;

  private String lastPerformedAction;

  private LocalDateTime lastActionDateTime;

  //  private String location;

  private String type;
  //
  //  private Map<String, Object> properties;
  //
  //  private List<String> members;
  //
  //  private String description;
  //
  //  private List<String> files;
  //
  //  private List<String> links;
  //
  //  private List<String> comments;
}
