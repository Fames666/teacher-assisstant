package by.ezubkova.teacher_assistant.library.api.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AttachmentMetadataResponse {

  private Long id;
  private String name;
  private String extension;
  private BigDecimal size;
}
