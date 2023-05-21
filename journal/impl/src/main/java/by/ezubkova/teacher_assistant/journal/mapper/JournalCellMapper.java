package by.ezubkova.teacher_assistant.journal.mapper;

import by.ezubkova.teacher_assistant.journal.api.model.JournalCellResponse;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JournalCellMapper {

  @Mapping(target = "date", source = "id.date")
  @Mapping(target = "highlightType", expression = "java(entity.getHighlightType().getValue())")
  JournalCellResponse toResponse(JournalCell entity);
}
