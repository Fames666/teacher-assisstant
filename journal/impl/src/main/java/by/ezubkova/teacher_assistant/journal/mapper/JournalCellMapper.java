package by.ezubkova.teacher_assistant.journal.mapper;

import by.ezubkova.teacher_assistant.journal.api.model.JournalCellResponse;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JournalCellMapper {

  @Mapping(target = "rowId", source = "journalRow.pkey.journal.id")
  @Mapping(target = "studentId", source = "journalRow.pkey.student.id")
  @Mapping(target = "highlightType", expression = "java(entity.getHighlightType().name())")
  JournalCellResponse toResponse(JournalCell entity);
}
