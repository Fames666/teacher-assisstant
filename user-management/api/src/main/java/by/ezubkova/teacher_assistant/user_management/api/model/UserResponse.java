package by.ezubkova.teacher_assistant.user_management.api.model;

import java.util.Set;
import lombok.Data;

@Data
public class UserResponse {

  private String id;

  private String username;

  private Set<String> roles;

  private boolean accountNonExpired;

  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  private boolean enabled;
}
