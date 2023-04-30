package by.ezubkova.teacher_assistant.journal.config;

import static by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight.*;
import static by.ezubkova.teacher_assistant.journal.constant.JournalRowDecoration.CROSSED_OUT;
import static by.ezubkova.teacher_assistant.journal.constant.JournalRowDecoration.OUTLINED;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static java.time.Year.isLeap;
import static java.util.stream.Stream.generate;

import by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight;
import by.ezubkova.teacher_assistant.journal.constant.JournalRowDecoration;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/**
 * REMOVE @GeneratedValue FROM ENTITIES BEFORE USE
 */
@Profile("generate-test-data")
@Configuration("JournalTestDataSupplier")
@DependsOn("UserManagementTestDataSupplier")
@Transactional
public class JournalTestDataSupplier {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JournalRepository journalRepository;
  @Autowired
  private JournalRowRepository journalRowRepository;

  public void initDb() {
    var users = userRepository.findAll();
    short year = (short) now().getYear();
    var journal = new Journal(1L, (byte) 5, 'A', year);
    var journalRows = createTestJournalRows(List.of(journal), users);

    journal.setRows(journalRows);
    for (var row : journalRows) {
      row.setCells(createTestJournalCells(year));
      row.getCells().forEach(cell -> cell.getId().setRow(row));
    }
    journalRepository.save(journal);
  }

  private List<JournalCell> createTestJournalCells(final int year) {
    var daysInYear = isLeap(year) ? 366 : 365;
    var dates = createTestLocalDates(year, daysInYear);
    var highlights = createTestHighlights(daysInYear);
    var marks = createTestMarks(daysInYear);
    var uncertaintyFlags = createTestUncertaintyFlags(daysInYear);
    var uncertaintyDescriptions = createTestUncertaintyDescriptions(uncertaintyFlags);

    var highlightsIter = highlights.iterator();
    var marksIter = marks.iterator();
    var uncertaintyFlagsIter = uncertaintyFlags.iterator();
    var uncertaintyDescriptionsIter = uncertaintyDescriptions.iterator();

    var cells = new ArrayList<JournalCell>(daysInYear);
    for (var date : dates) {
      cells.add(new JournalCell(date,
                                highlightsIter.next(),
                                marksIter.next(),
                                uncertaintyFlagsIter.next(),
                                uncertaintyDescriptionsIter.next()));
    }
    return cells;
  }

  private List<LocalDate> createTestLocalDates(final int year, final int daysInYear) {
    var offset = new AtomicInteger(0);
    var september1st = of(year, Month.SEPTEMBER, 1);
    return generate(() -> september1st.plusDays(offset.getAndIncrement()))
        .limit(daysInYear)
        .toList();
  }

  private List<JournalCellHighlight> createTestHighlights(final int daysInYear) {
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
    }).limit(daysInYear).toList();
  }

  private List<Byte> createTestMarks(final int daysInYear) {
    var random = new Random();
    return generate(() -> random.nextDouble() < 0.3 ? (byte) random.nextInt() : null)
        .limit(daysInYear).toList();
  }

  private List<Boolean> createTestUncertaintyFlags(final int daysInYear) {
    var random = new Random();
    return generate(() -> random.nextDouble() < 0.07)
        .limit(daysInYear).toList();
  }

  private List<String> createTestUncertaintyDescriptions(List<Boolean> uncertaintyFlags) {
    return uncertaintyFlags.stream()
        .map(flag -> flag ? "Need to ask this student again" : null)
        .toList();
  }

  private List<JournalRow> createTestJournalRows(List<Journal> journals, List<User> users) {
    var rowsAmount = journals.size() * users.size();
    var rowDecorations = createTestJournalRowsDecorations(rowsAmount);
    var averageMarks = createTestAverageMarks(rowsAmount);

    var usersIter = users.iterator();
    var rowDecorationsIter = rowDecorations.iterator();
    var averageMarksIter = averageMarks.iterator();

    var rows = new ArrayList<JournalRow>(rowsAmount);
    for (var journal : journals) {
      for (int i = 0; i < rowsAmount; i++) {
        var row = new JournalRow(journal,
                                 usersIter.next(),
                                 rowDecorationsIter.next(),
                                 averageMarksIter.next());
        rows.add(row);
      }
    }
    return rows;
  }

  private List<JournalRowDecoration> createTestJournalRowsDecorations(final int rowsAmount) {
    var random = new Random();
    return generate(() -> {
      var value = random.nextDouble();
      if (value < 0.07) {
        return CROSSED_OUT;
      }
      else if (value < 0.1) {
        return OUTLINED;
      }
      return JournalRowDecoration.NONE;
    }).limit(rowsAmount).toList();
  }

  private List<Float> createTestAverageMarks(final int rowsAmount) {
    var random = new Random();
    return generate(() -> {
      var value = random.nextDouble();
      return ((long) (value * 1000)) * 1.0 / 100;
    }).map(Double::floatValue).limit(rowsAmount).toList();
  }
}
