package by.ezubkova.teacher_assistant.journal.config;

import static by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight.*;
import static java.time.LocalDate.of;
import static java.util.stream.Stream.generate;

import by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight;
import by.ezubkova.teacher_assistant.journal.jpa.model.AcademicSemester;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRowRepository;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * REMOVE @GeneratedValue FROM ENTITIES BEFORE USE
 */
@Profile("generate-test-data")
@Configuration("JournalTestDataSupplier")
@Transactional
public class JournalTestDataSupplier {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JournalRepository journalRepository;
  @Autowired
  private JournalRowRepository journalRowRepository;

  public void initDb() {
    var students = userRepository.findAll().stream()
                                 .filter(user -> user.getUsername().startsWith("test_student"))
                                 .toList();
    var journals = journalRepository.findAll();
    if (journalRowRepository.count() > 0) {
      return;
    }
    for (var journal : journals) {
      journal.setRows(createTestJournalRows(journal, students));
    }
    journalRepository.saveAllAndFlush(journals);
  }

  private List<JournalCell> createTestJournalCells(AcademicSemester semester) {
    var daysInSemester = Period.between(semester.getStartDate(), semester.getEndDate()).getDays();
    var dates = createTestLocalDates(semester.getYear(), daysInSemester);
    var highlights = createTestHighlights(daysInSemester);
    var marks = createTestMarks(daysInSemester);
    var uncertaintyFlags = createTestUncertaintyFlags(daysInSemester);
    var uncertaintyDescriptions = createTestUncertaintyDescriptions(uncertaintyFlags);

    var highlightsIter = highlights.iterator();
    var marksIter = marks.iterator();
    var uncertaintyFlagsIter = uncertaintyFlags.iterator();
    var uncertaintyDescriptionsIter = uncertaintyDescriptions.iterator();

    var cells = new ArrayList<JournalCell>(daysInSemester);
    for (var date : dates) {
      cells.add(new JournalCell(date,
                                highlightsIter.next(),
                                marksIter.next(),
                                uncertaintyFlagsIter.next(),
                                uncertaintyDescriptionsIter.next()));
    }
    return cells;
  }

  private List<LocalDate> createTestLocalDates(final int year, final int daysInSemester) {
    var offset = new AtomicInteger(0);
    var september1st = of(year, Month.SEPTEMBER, 1);
    return generate(() -> september1st.plusDays(offset.getAndIncrement()))
        .limit(daysInSemester)
        .toList();
  }

  private List<JournalCellHighlight> createTestHighlights(final int daysInSemester) {
    var random = new Random();
    return generate(() -> {
      var value = random.nextDouble();
      if (value < 0.07) {
        return HIGH_PRIORITY;
      }
      else if (value < 0.1) {
        return LOW_PRIORITY;
      }
      return NONE;
    }).limit(daysInSemester).toList();
  }

  private List<Byte> createTestMarks(final int daysInSemester) {
    var random = new Random();
    return generate(() -> random.nextDouble() < 0.3 ? (byte) random.nextInt() : null)
        .limit(daysInSemester).toList();
  }

  private List<Boolean> createTestUncertaintyFlags(final int daysInSemester) {
    var random = new Random();
    return generate(() -> random.nextDouble() < 0.07)
        .limit(daysInSemester).toList();
  }

  private List<String> createTestUncertaintyDescriptions(List<Boolean> uncertaintyFlags) {
    return uncertaintyFlags.stream()
                           .map(flag -> flag ? "Need to ask this student again" : null)
                           .toList();
  }

  private List<JournalRow> createTestJournalRows(Journal journal, List<User> users) {
    var rowsAmount = users.size();
    var averageMarks = createTestAverageMarks(rowsAmount);

    var usersIter = users.iterator();
    var averageMarksIter = averageMarks.iterator();

    var rows = new ArrayList<JournalRow>(rowsAmount);
    for (int i = 0; i < rowsAmount; i++) {
      var cells = createTestJournalCells(journal.getAcademicSemester());
      var row = new JournalRow(journal,
                               usersIter.next(),
                               averageMarksIter.next(),
                               false,
                               null,
                               cells);
      cells.forEach(cell -> cell.getId().setRow(row));
      rows.add(row);
    }
    return rows;
  }

  private List<Float> createTestAverageMarks(final int rowsAmount) {
    var random = new Random();
    return generate(() -> {
      var value = random.nextDouble();
      return ((long) (value * 1000)) * 1.0 / 100;
    }).map(Double::floatValue).limit(rowsAmount).toList();
  }
}
