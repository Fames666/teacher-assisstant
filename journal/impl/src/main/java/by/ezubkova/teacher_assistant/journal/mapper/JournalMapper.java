package by.ezubkova.teacher_assistant.journal.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;

import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(uses = JournalRowMapper.class)
public interface JournalMapper {

  @IterableMapping(nullValueMappingStrategy = RETURN_DEFAULT)
  JournalResponse toResponse(Journal entity);
}
