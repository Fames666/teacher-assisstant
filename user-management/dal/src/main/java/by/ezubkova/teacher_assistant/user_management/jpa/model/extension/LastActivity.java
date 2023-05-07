package by.ezubkova.teacher_assistant.user_management.jpa.model.extension;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Embeddable
public class LastActivity {

//  @Column(nullable = false)
  private Boolean online;

  private LocalDateTime lastOnlineAt;
}
