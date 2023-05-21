package by.ezubkova.teacher_assistant.journal.api.service;

import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalRowResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface JournalService {

  List<JournalPreviewResponse> findJournalsAvailableForUser(@NotNull String userId);

  List<JournalUserPreviewResponse> createJournalUsersPreviews(@NotNull Long journalId);

  JournalResponse getJournal(@NotNull Long journalId);

  // TODO: add mark validation
  JournalRowResponse putMark(@NotNull Long journalId,
                             @NotNull String studentId,
                             @NotNull LocalDate date,
                             @NotNull Byte mark);

  JournalRowResponse toggleRemark(@NotNull Long journalId,
                                  @NotNull String studentId,
                                  @NotNull LocalDate date);
}
