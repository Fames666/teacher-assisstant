package by.ezubkova.teacher_assistant.user_management.api.service;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public interface UserUtilService {

  String formatFullName(@NotNull String firstName,
                        @NotNull String secondName,
                        @Nullable String thirdName);

  String formatLastOnlineStatus(@NotNull Boolean isOnline, @Nullable LocalDateTime lastOnline);
}
