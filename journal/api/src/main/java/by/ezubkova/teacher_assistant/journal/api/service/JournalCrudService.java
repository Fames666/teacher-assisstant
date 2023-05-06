package by.ezubkova.teacher_assistant.journal.api.service;

import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface JournalCrudService {

  void getJournal(@NotNull Long id);

  JournalResponse getJournalByParameters(@NotNull Byte classNumber,
                                         @NotNull Character classLetter,
                                         @NotNull Short year);

  List<JournalPreviewResponse> readJournalsAvailableForUser(@NotNull String userId);
}
