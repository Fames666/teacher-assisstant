package by.ezubkova.teacher_assistant.journal.mapper;

import static by.ezubkova.teacher_assistant.common.util.CommonUtils.buildFullName;
import static by.ezubkova.teacher_assistant.common.util.CommonUtils.capitalizeFirstLetter;
import static java.time.format.TextStyle.*;
import static java.util.Locale.forLanguageTag;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import by.ezubkova.teacher_assistant.common.util.CollectionUtils;
import by.ezubkova.teacher_assistant.journal.api.model.AcademicSemesterResponse;
import by.ezubkova.teacher_assistant.journal.api.model.AcademicSemesterResponse.AcademicSemesterPartition;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.jpa.model.AcademicSemester;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = JournalRowMapper.class)
public interface JournalMapper {

  @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
  JournalResponse toResponse(Journal entity);

  @Mapping(target = "partitions", source = ".")
  AcademicSemesterResponse toAcademicSemesterResponse(AcademicSemester entity);

  // TODO: fix locale
  default List<AcademicSemesterPartition> toAcademicSemesterPartition(AcademicSemester entity) {
    var startPoint = entity.getStartDate();
    var endPoint = entity.getEndDate();
    var partitions = CollectionUtils.<AcademicSemesterPartition>newArrayList();

    var pointWithinPeriod = startPoint;
    do {
      var month = pointWithinPeriod.getMonth();
      var monthName = month.getDisplayName(FULL_STANDALONE, forLanguageTag("ru-RU"));
      monthName = capitalizeFirstLetter(monthName);
      var daysOfMonth = getDaysOfMonth(startPoint, endPoint, month, entity.getYear());

      partitions.add(new AcademicSemesterPartition(monthName, daysOfMonth));
      pointWithinPeriod = pointWithinPeriod.plusMonths(1);
    } while (pointWithinPeriod.getMonthValue() <= endPoint.getMonthValue());

    return partitions;
  }

  default Map<Integer, String> getDaysOfMonth(LocalDate semesterStartPoint,
                                              LocalDate semesterEndPoint,
                                              Month month,
                                              int year) {
    int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
    var startOfMonth = LocalDate.of(year, month, 1);
    var endOfMonth = LocalDate.of(year, month, lengthOfMonth % 10 == 0 ? 30 : 31);

    var daysOfWeek = new HashMap<Integer, String>();
    var currentCountPoint = semesterStartPoint.isAfter(startOfMonth) ? semesterStartPoint : startOfMonth;
    var countEndPoint = semesterEndPoint.isBefore(endOfMonth) ? semesterEndPoint : endOfMonth;
    while (!currentCountPoint.isAfter(countEndPoint)) {
      var displayName = currentCountPoint.getDayOfWeek()
                                         .getDisplayName(SHORT_STANDALONE, forLanguageTag("ru-RU"));
      daysOfWeek.put(currentCountPoint.getDayOfMonth(), displayName);
      currentCountPoint = currentCountPoint.plusDays(1);
    }
    return daysOfWeek;
  }

  default String toLeadTeacherResponse(User entity) {
    return buildFullName(entity.getUserData());
  }
}
