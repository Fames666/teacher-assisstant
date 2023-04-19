package by.ezubkova.teacher_assistant.journal.mapper;

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

  String METHOD_MAP_AVERAGE_MARK = "mapAverageMark";

  @Mapping(target = "journalId", source = "pkey.journal.id")
  @Mapping(target = "student", source = "pkey.student")
  @Mapping(target = "decorationType", expression = "java(entity.getDecorationType().name())")
  @Mapping(target = "averageMark", source = "averageMark", qualifiedByName = METHOD_MAP_AVERAGE_MARK)
  @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
  JournalRowResponse toResponse(JournalRow entity);

  @Mapping(target = "firstName", source = "userData.firstName")
  @Mapping(target = "secondName", source = "userData.secondName")
  @Mapping(target = "thirdName", source = "userData.thirdName")
  StudentData toStudentData(User student);

  @Named(METHOD_MAP_AVERAGE_MARK)
  default BigDecimal mapAverageMark(Double averageMark) {
    return valueOf(averageMark).setScale(2, HALF_UP);
  }
}
