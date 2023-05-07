package by.ezubkova.teacher_assistant.user_management.service;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.EMPTY;
import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.SPACE;
import static by.ezubkova.teacher_assistant.common.util.CollectionUtils.firstNonNull;
import static by.ezubkova.teacher_assistant.user_management.constant.UserManagementConstants.*;
import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import by.ezubkova.teacher_assistant.user_management.api.service.UserUtilService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserUtilService implements UserUtilService {

  private static final byte HOURS_IN_DAY = 24;
  private static final byte MINUTES_IN_HOUR = 60;
  private static final byte SECONDS_IN_MINUTE = 60;
  private static final int ACTIVITY_STATUS_MINUTES_NO_STEP = 0;
  private static final int ACTIVITY_STATUS_MINUTES_INITIAL_STEP = 5;
  private static final int ACTIVITY_STATUS_MINUTES_HALF_HOUR_STEP = 30;

  private static final Set<Integer> ACTIVITY_STATUS_MINUTES_STEPS
      = Set.of(ACTIVITY_STATUS_MINUTES_NO_STEP, ACTIVITY_STATUS_MINUTES_INITIAL_STEP, 10, 15, ACTIVITY_STATUS_MINUTES_HALF_HOUR_STEP, 45);

  private static final Map<Integer, String> ACTIVITY_STATUS_SPECIAL_MESSAGES_MAPPING = Map.of(
      ACTIVITY_STATUS_MINUTES_NO_STEP, USER_ACTIVITY_ONLINE,
      ACTIVITY_STATUS_MINUTES_INITIAL_STEP, USER_ACTIVITY_OFFLINE_SECOND_AGO,
      ACTIVITY_STATUS_MINUTES_HALF_HOUR_STEP, USER_ACTIVITY_OFFLINE_HALF_HOUR_AGO
  );

  @Qualifier(USER_MANAGEMENT_MODULE_MSG_SRC)
  private final PrefixedMessageSource messageSource;

  @Override
  public String formatFullName(String firstName, String secondName, String thirdName) {
    var builder = new StringBuilder(secondName)
        .append(SPACE).append(firstName);
    if (thirdName != null) {
      builder.append(SPACE).append(thirdName);
    }
    return builder.toString();
  }

  @Override
  public String formatLastOnlineStatus(Boolean isOnline, LocalDateTime lastOnline) {
    if (isOnline) {
      return messageSource.getMessage(USER_ACTIVITY_ONLINE);
    }
    if (lastOnline == null) {
      return messageSource.getMessage(USER_ACTIVITY_OFFLINE_EVER);
    }
    var minutesAfterLastOnline = between(now(), lastOnline).getSeconds() / SECONDS_IN_MINUTE;
    return firstNonNull(EMPTY,
                        formatLastOnlineMinutesAgoMessage(minutesAfterLastOnline),
                        formatLastOnlineHoursAgoMessage(minutesAfterLastOnline),
                        formatLastOnlineDaysAgoMessage(minutesAfterLastOnline));
  }

  @Nullable
  private String formatLastOnlineMinutesAgoMessage(final long minutesAfterLastOnline) {
    if (minutesAfterLastOnline >= MINUTES_IN_HOUR) {
      return null;
    }

    int previousStep = ACTIVITY_STATUS_MINUTES_NO_STEP;
    for (int step : ACTIVITY_STATUS_MINUTES_STEPS) {
      if (minutesAfterLastOnline > step) {
        previousStep = step;
        continue;
      }

      var specialMessageCode = ACTIVITY_STATUS_SPECIAL_MESSAGES_MAPPING.get(previousStep);
      if (specialMessageCode != null) {
        return messageSource.getMessage(specialMessageCode);
      }
      return messageSource.getMessage(USER_ACTIVITY_OFFLINE_MINUTES_AGO_TPL, previousStep);
    }
    return null;  // never reachable
  }

  @Nullable
  private String formatLastOnlineHoursAgoMessage(final long minutesAfterLastOnline) {
    final long hoursAfterLastOnline = minutesAfterLastOnline / MINUTES_IN_HOUR;
    if (hoursAfterLastOnline >= HOURS_IN_DAY) {
      return null;
    }
    return messageSource.getMessage(USER_ACTIVITY_OFFLINE_HOURS_AGO_TPL, hoursAfterLastOnline);
  }

  @Nullable
  private String formatLastOnlineDaysAgoMessage(final long minutesAfterLastOnline) {
    final long daysAfterLastOnline = minutesAfterLastOnline / MINUTES_IN_HOUR / HOURS_IN_DAY;
    return messageSource.getMessage(USER_ACTIVITY_OFFLINE_DAYS_AGO_TPL, daysAfterLastOnline);
  }
}
