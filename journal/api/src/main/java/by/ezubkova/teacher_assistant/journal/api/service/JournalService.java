package by.ezubkova.teacher_assistant.journal.api.service;

import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface JournalService {

  void getJournal(@NotNull Long id);

  JournalResponse getJournalByParameters(@NotNull Byte classNumber,
                                         @NotNull Character classLetter,
                                         @NotNull Short year);

  List<JournalPreviewResponse> findJournalsAvailableForUser(@NotNull String userId);

  List<JournalUserPreviewResponse> createJournalUsersPreviews(@NotNull Long journalId);
}
