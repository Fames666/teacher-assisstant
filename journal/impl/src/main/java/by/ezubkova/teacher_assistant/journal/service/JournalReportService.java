package by.ezubkova.teacher_assistant.journal.service;

import static java.util.Collections.singletonList;
import static java.util.Locale.getDefault;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import by.ezubkova.teacher_assistant.document_management.api.model.ConclusiveProgressReportRow;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JournalReportService {

  public final Object[] noArgs = new Object[0];

  // TODO: move to api in doc-service
  public static final String CPR_BODY_TIME_RANGE_YEAR = "conclusive-progress-report.body.time-range.year";

  private JournalRepository journalRepository;
  private UserRepository userRepository;
  private MessageSource messages;

  public List<ConclusiveProgressReportRow> fetchDataForConclusiveProgressReport(String teacherId) {
    var teacher = findTeacher(teacherId);
    var journals = journalRepository.findAllByTeacher(teacher);  // TODO: add year param
    var groupedByClassJournals = new HashMap<String, List<Journal>>();
    journals.forEach(journal -> appendValueToListsMap(groupedByClassJournals,
                                                      journal.composeFullClassName(),
                                                      journal));
    return groupedByClassJournals.entrySet().stream()
                                 .map(entry -> populateConclusiveProgressReportRequestList(entry.getKey(), entry.getValue()))
                                 .flatMap(Collection::stream)
                                 .toList();
  }

  public List<ConclusiveProgressReportRow> populateConclusiveProgressReportRequestList(String className,
                                                                                       List<Journal> classJournals) {
    var requests = classJournals
        .stream()
        .map(journal -> {
          var studentsAmount = (short) journal.getRows().size();
          var period = journal.getSemester().toString();
          var reportRow = new ConclusiveProgressReportRow(className, studentsAmount, period);
          populateConclusiveProgressReportRequest(className, singletonList(journal), reportRow);
          return reportRow;
        })
        .collect(collectingAndThen(toList(), ArrayList::new));

    var studentsAmount = (short) classJournals.stream()
                                              .map(Journal::getRows)
                                              .mapToLong(Collection::size)
                                              .sum();
    var period = messages.getMessage(CPR_BODY_TIME_RANGE_YEAR, noArgs, getDefault());
    var reportRow = new ConclusiveProgressReportRow(className, studentsAmount, period);
    populateConclusiveProgressReportRequest(className, classJournals, reportRow);

    requests.add(reportRow);
    return requests;
  }

  private void populateConclusiveProgressReportRequest(String className,
                                                       List<Journal> journals,
                                                       ConclusiveProgressReportRow reportRow) {
    var naCount = countNotAttestedStudents(journals);
    reportRow.setNaCount(naCount.get(false));
    reportRow.setNaWithReasonCount(naCount.get(true));
    reportRow.setMarkToCountMap(countMarks(journals));
  }

  private User findTeacher(String teacherId) {
    return userRepository.findById(teacherId)
                         .orElseThrow(() -> new IllegalStateException("No user found"));  // TODO: improve
  }

  // TODO: move to utils?
  private <K, V> void appendValueToListsMap(Map<K, List<V>> map, K key, V value) {
    map.computeIfAbsent(key, __ -> new ArrayList<>()).add(value);
  }

  private Map<Byte, Integer> countMarks(List<Journal> journals) {
    var countToMarkMap = new HashMap<Byte, Integer>();
    journals.stream()
            .flatMap(journal -> journal.getRows().stream())
            .flatMap(row -> row.getCells().stream())
            .filter(cell -> cell.getMark() != null && !cell.isUncertain())
            .map(JournalCell::getMark)
            .forEach(mark -> {
              countToMarkMap.computeIfAbsent(mark, __ -> 0);
              countToMarkMap.computeIfPresent(mark, (__, count) -> ++count);
            });
    return countToMarkMap;
  }

  private Map<Boolean, Integer> countNotAttestedStudents(List<Journal> journals) {
    var countToNaMap = new HashMap<Boolean, Integer>();
    journals.stream()
            .flatMap(journal -> journal.getRows().stream())
            .filter(JournalRow::isNotAttested)
            .forEach(row -> {
              var hasReason = row.getNotAttestedReason() != null;
              countToNaMap.computeIfAbsent(hasReason, __ -> 0);
              countToNaMap.computeIfPresent(hasReason, (__, count) -> ++count);
            });
    return countToNaMap;
  }
}
