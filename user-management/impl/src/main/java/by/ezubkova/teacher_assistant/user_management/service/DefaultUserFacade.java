package by.ezubkova.teacher_assistant.user_management.service;

import by.ezubkova.teacher_assistant.user_management.api.service.UserFacade;
import by.ezubkova.teacher_assistant.user_management.api.service.UserUtilService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserFacade implements UserFacade {

  private final UserUtilService userUtilService;

  @Override
  public String formatLastOnlineStatus(Boolean isOnline, LocalDateTime lastOnline) {
    return userUtilService.formatLastOnlineStatus(isOnline, lastOnline);
  }

  @Override
  public String formatFullName(String firstName, String secondName, String thirdName) {
    return userUtilService.formatFullName(firstName, secondName, thirdName);
  }
}
