package by.ezubkova.teacher_assistant.user_management.api.service;

import by.ezubkova.teacher_assistant.user_management.api.model.UserResponse;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserManagementFacade {

  Optional<UserResponse> getUserById(String id);
}
