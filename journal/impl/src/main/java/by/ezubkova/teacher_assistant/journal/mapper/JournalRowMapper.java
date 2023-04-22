package by.ezubkova.teacher_assistant.journal.mapper;

import org.mapstruct.Mapper;

@Mapper(uses = JournalCellMapper.class)
public interface JournalRowMapper {

  String METHOD_MAP_AVERAGE_MARK = "mapAverageMark";

//  @Mapping(target = "journalId", source = "id.journal.id")
//  @Mapping(target = "student", source = "id.student")
//  @Mapping(target = "decorationType", expression = "java(entity.getDecorationType().name())")
//  @Mapping(target = "averageMark", source = "averageMark", qualifiedByName = METHOD_MAP_AVERAGE_MARK)
//  @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
//  JournalRowResponse toResponse(JournalRow entity);

//  @Mapping(target = "firstName", source = "userData.firstName")
//  @Mapping(target = "secondName", source = "userData.secondName")
//  @Mapping(target = "thirdName", source = "userData.thirdName")
//  StudentData toStudentData(User student);
//
//  @Named(METHOD_MAP_AVERAGE_MARK)
//  default BigDecimal mapAverageMark(Double averageMark) {
//    return valueOf(averageMark).setScale(2, HALF_UP);
//  }
}
