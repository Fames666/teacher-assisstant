package by.ezubkova.teacher_assistant.journal.mapper;

import static by.ezubkova.teacher_assistant.common.util.CommonUtils.buildFullName;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import by.ezubkova.teacher_assistant.journal.api.model.JournalRowResponse;
import by.ezubkova.teacher_assistant.journal.api.model.StudentData;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.math.BigDecimal;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(uses = JournalCellMapper.class)
public interface JournalRowMapper {

  String METHOD_MAP_AVERAGE_MARK_TO_RESPONSE = "mapAverageMarkToResponse";
  String MAP_PROGRESS_LEVEL_TO_RESPONSE = "mapProgressLevelToResponse";

  @Mapping(target = "journalId", source = "id.journal.id")
  @Mapping(target = "student", source = "id.student")
  @Mapping(target = "averageMark", source = "averageMark", qualifiedByName = METHOD_MAP_AVERAGE_MARK_TO_RESPONSE)
  @Mapping(target = "progressLevel", source = "averageMark", qualifiedByName = MAP_PROGRESS_LEVEL_TO_RESPONSE)
  @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
  JournalRowResponse toResponse(JournalRow entity);

  @Named(METHOD_MAP_AVERAGE_MARK_TO_RESPONSE)
  default BigDecimal mapAverageMarkToResponse(Float averageMark) {
    return valueOf(averageMark).setScale(2, HALF_UP);
  }

  @Named(MAP_PROGRESS_LEVEL_TO_RESPONSE)
  default Byte mapProgressLevelToResponse(Float averageMark) {
    return (byte) (averageMark * 10);
  }

  default StudentData toStudentDataResponse(User user) {
    var fullName = buildFullName(user.getUserData());
    return new StudentData(user.getId(), fullName);
  }
}
