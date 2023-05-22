package by.ezubkova.teacher_assistant.document_management.service;

import static by.ezubkova.teacher_assistant.document_management.constant.Measurements.*;
import static by.ezubkova.teacher_assistant.document_management.constant.Messages.*;
import static com.lowagie.text.Element.*;
import static com.lowagie.text.Font.DEFAULTSIZE;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.pdf.BaseFont.IDENTITY_H;
import static com.lowagie.text.pdf.BaseFont.createFont;
import static com.lowagie.text.pdf.PdfWriter.getInstance;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Arrays.fill;
import static java.util.Locale.getDefault;
import static java.util.stream.Stream.generate;
import static java.util.stream.Stream.of;

import by.ezubkova.teacher_assistant.common.util.CommonUtils;
import by.ezubkova.teacher_assistant.document_management.config.ConclusiveProgressReportProperties;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ConclusiveProgressReportGeneratorService {

  private final Object[] noArgs = new Object[0];
  private final Font defaultFont;

  private final MessageSource messages;
  private final ConclusiveProgressReportProperties properties;
  private final JournalRepository journalRepository;

  private static int teor = 0;
  private static int pract = 0;
  private static Map<Integer, Integer> marksCounts = new HashMap<>(Map.of(
      1, 0,
      2, 0,
      3, 0,
      4, 0,
      5, 0,
      6, 0,
      7, 0,
      8, 0,
      9, 0,
      10, 0
  ));

  @Autowired
  public ConclusiveProgressReportGeneratorService(@Qualifier("document-management-message-source") MessageSource messages,
                                                  ConclusiveProgressReportProperties properties,
                                                  JournalRepository journalRepository) {
    this.messages = messages;
    this.properties = properties;
    this.journalRepository = journalRepository;
    this.defaultFont = initializeFont();
  }

  public void generateConclusiveProgressReport(Byte classNumber, String classLetter, Short year) {
    try (var document = new Document(A4.rotate(),
                                     properties.minDocumentMargin(),
                                     properties.minDocumentMargin(),
                                     properties.maxDocumentMargin(),
                                     properties.minDocumentMargin())) {
      getInstance(document, new FileOutputStream("tables.pdf"));
      document.open();

      var table = new PdfPTable(properties.totalColumns());
      applyConstraintsToReportTable(table, document);
      createHeader(table);

      var journals = journalRepository.findAllByClassAndYear(classNumber, classLetter.toCharArray()[0], year);
      createResultsTrack(table, journals);
      var teacher = CommonUtils.buildFullName(journals.get(0).getLeadTeacher().getUserData());
      var cell = createCell(teacher + ", учитель математики", 1, 19, false);
      cell.setHorizontalAlignment(ALIGN_LEFT);
      table.addCell(cell);

      document.add(table);
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);  // TODO: replace with more explicit except.
    }
  }

  private void applyConstraintsToReportTable(PdfPTable table, Document document) {
    var tableWidth = document.getPageSize().getWidth() - 2 * properties.minDocumentMargin();
    var columnsAmount = properties.totalColumns();
    var relativeColumnsWidths = new float[columnsAmount];
    fill(relativeColumnsWidths, 1);
    relativeColumnsWidths[columnsAmount - 1] =
        relativeColumnsWidths[columnsAmount - 2] = 2.5F;

    table.setHorizontalAlignment(ALIGN_CENTER);
    table.setTotalWidth(tableWidth);
    table.setWidths(relativeColumnsWidths);
    table.setLockedWidth(true);
  }

  private void createHeader(PdfPTable table) {
    table.addCell(createCellWithText(CPR_HEADER_CLASS, 2, 1, true));
    table.addCell(createCellWithText(CPR_HEADER_STUDENTS, 2, 1, true));
    table.addCell(createCellWithText(CPR_HEADER_TIME_PERIOD, 2, 1, true));
    table.addCell(createCellWithText(CPR_HEADER_MARKS, 1, CONCLUSIVE_PROGRESS_REPORT_MARKS_COLUMNS, false));
    table.addCell(createCellWithText(CPR_HEADER_NA, 2, 1, false));
    table.addCell(createCellWithText(CPR_HEADER_NA_REASON, 2, 1, true));
    table.addCell(createCellWithText(CPR_HEADER_LEARNING_LEVEL, 2, 1, false));
    table.addCell(createCellWithText(CPR_HEADER_KNOWLEDGE_QUALITY, 2, 1, false));
    table.addCell(createCellWithText(CPR_HEADER_PROGRAM_COMPLETION, 1, 2, false));

    createMarksCells(table);
    table.addCell(createCellWithText(CPR_HEADER_THEORETICAL_PART, 1, 1, false));
    table.addCell(createCellWithText(CPR_HEADER_PRACTICAL_PART, 1, 1, false));

    applyRowFixedHeight(table.getRow(0), cmToPoints(0.7F));
    applyRowFixedHeight(table.getRow(1), cmToPoints(1.8F));
  }

  private void createMarksCells(PdfPTable table) {
    var counter = new AtomicInteger(0);
    generate(counter::incrementAndGet)
        .limit(properties.totalMarksColumns())
        .map(value -> createCell(value.toString(), 1, 1, false))
        .forEach(table::addCell);
  }

  private PdfPCell createCellWithText(String msgCode, int rowSpan, int colSpan, boolean vertical) {
    var message = messages.getMessage(msgCode, noArgs, getDefault());
    return createCell(message, rowSpan, colSpan, vertical);
  }

  private void applyRowFixedHeight(PdfPRow row, float height) {
    of(row.getCells()).filter(Objects::nonNull)
                      .forEach(cell -> cell.setFixedHeight(height));
    row.calculateHeights();
  }

  private void createResultsTrack(PdfPTable table, List<Journal> journals) {
    var className = String.format("%s%s", journals.get(0).getClassNumber(), journals.get(0).getClassLetter());
    createTrackRow(table, className, journals.get(0));

    createTrackRow(table, "", journals.get(1));
    createTrackRow(table, "", journals.get(2));
    createTrackRow(table, "", journals.get(3));

//    var lastTrackCellText = messages.getMessage(CPR_BODY_TIME_RANGE_YEAR, noArgs, getDefault());
    var yearStudents = journals.get(0).getRows().size() + journals.get(1).getRows().size()
        + journals.get(2).getRows().size() + journals.get(3).getRows().size();
    createYearRow(table, journals.get(3).getRows().size(), yearStudents);

    teor = 0;
    pract = 0;
    marksCounts.entrySet().forEach(entry -> entry.setValue(0));
  }

  private void createYearRow(PdfPTable table, int studentsAmount, int studentsDivider) {
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(studentsAmount), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("год", DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell(String.valueOf(marksCounts.get(1)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(2)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(3)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(4)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(5)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(6)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(7)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(8)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(9)), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(marksCounts.get(10)), DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell(calcKnowledgeQuality(5, studentsDivider,
                                                  String.valueOf(marksCounts.get(10)), String.valueOf(marksCounts.get(9)),
                                                  String.valueOf(marksCounts.get(8)), String.valueOf(marksCounts.get(7)),
                                                  String.valueOf(marksCounts.get(6)), String.valueOf(marksCounts.get(5))), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(calcKnowledgeQuality(6, studentsDivider,
                                                  String.valueOf(marksCounts.get(10)), String.valueOf(marksCounts.get(9)),
                                                  String.valueOf(marksCounts.get(8)), String.valueOf(marksCounts.get(7)),
                                                  String.valueOf(marksCounts.get(6))), DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell(String.valueOf(teor), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(String.valueOf(pract), DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private void createTrackRow(PdfPTable table, String className, Journal journal) {
    var studentsAmount = String.valueOf(journal.getRows().size());
    var studentsAmountInt = Integer.parseInt(studentsAmount);
    var semester = journal.getAcademicSemester().getSemester().toString();

    table.addCell(createCell(className, DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(studentsAmount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(semester, DEFAULT_SPAN, DEFAULT_SPAN, false));

    var onesCount = countMarks(journal, 1);
    marksCounts.put(1, marksCounts.get(1) + Integer.parseInt(onesCount));
    table.addCell(createCell(onesCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var twosCount = countMarks(journal, 2);
    marksCounts.put(2, marksCounts.get(2) + Integer.parseInt(twosCount));
    table.addCell(createCell(twosCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var threesCount = countMarks(journal, 3);
    marksCounts.put(3, marksCounts.get(3) + Integer.parseInt(threesCount));
    table.addCell(createCell(threesCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var foursCount = countMarks(journal, 4);
    marksCounts.put(4, marksCounts.get(4) + Integer.parseInt(foursCount));
    table.addCell(createCell(foursCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var fivesCount = countMarks(journal, 5);
    marksCounts.put(5, marksCounts.get(5) + Integer.parseInt(fivesCount));
    table.addCell(createCell(fivesCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var sixesCount = countMarks(journal, 6);
    marksCounts.put(6, marksCounts.get(6) + Integer.parseInt(sixesCount));
    table.addCell(createCell(sixesCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var sevensCount = countMarks(journal, 7);
    marksCounts.put(7, marksCounts.get(7) + Integer.parseInt(sevensCount));
    table.addCell(createCell(sevensCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var eightsCount = countMarks(journal, 8);
    marksCounts.put(8, marksCounts.get(8) + Integer.parseInt(eightsCount));
    table.addCell(createCell(eightsCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var ninesCount = countMarks(journal, 9);
    marksCounts.put(9, marksCounts.get(9) + Integer.parseInt(ninesCount));
    table.addCell(createCell(ninesCount, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var tensCount = countMarks(journal, 10);
    marksCounts.put(10, marksCounts.get(10) + Integer.parseInt(tensCount));
    table.addCell(createCell(tensCount, DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));

    table.addCell(createCell(calcKnowledgeQuality(5, studentsAmountInt, tensCount, ninesCount, eightsCount, sevensCount, sixesCount, fivesCount), DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell(calcKnowledgeQuality(6, studentsAmountInt, tensCount, ninesCount, eightsCount, sevensCount, sixesCount), DEFAULT_SPAN, DEFAULT_SPAN, false));

    var teorP = random(29, 31);
    teor += Integer.parseInt(teorP);
    table.addCell(createCell(teorP, DEFAULT_SPAN, DEFAULT_SPAN, false));
    var practP = random(1, 2);
    pract += Integer.parseInt(practP);
    table.addCell(createCell(practP, DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private String countMarks(Journal journal, int mark) {
    var comparable = new BigDecimal(mark).setScale(0, HALF_UP);
    var amount = journal.getRows().stream()
                        .map(JournalRow::getAverageMark)
                        .filter(avg -> comparable.equals(new BigDecimal(avg).setScale(0, HALF_UP)))
                        .count();
    return String.valueOf(amount);
  }

  private String random(int a, int b) {
    var value = new Random().nextInt(a, b);
    return String.valueOf(value);
  }

  private String calcKnowledgeQuality(int limit, int students, String... counts) {
    int i = 10;
    int sum = 0;
    for (var count : counts) {
      int intCount = Integer.parseInt(count);
      sum += intCount;
      --i;
      if (i < limit) {
        break;
      }
    }
    return String.valueOf(sum * 100 / students);
  }

  private void tmp1(PdfPTable table, int ti) {
    table.addCell(createCell(valueOf(ti + 6) + "A", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("20", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("8", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("3", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("4", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("100", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("80", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("35", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private void tmp2(PdfPTable table, int ti) {
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("20", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("3", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("10", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("4", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("100", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("100", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("30", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private void tmp3(PdfPTable table, int ti) {
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("22", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("3", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("5", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("10", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("100", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("100", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("41", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private void tmp4(PdfPTable table, int it) {
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("22", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("4", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("5", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("8", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("2", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("90", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("78", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("32", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private void tmpYear(PdfPTable table, int ti) {
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("22", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("4", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("6", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("13", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("22", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("22", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("9", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("11", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("-", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("90", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("78", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("32", DEFAULT_SPAN, DEFAULT_SPAN, false));
    table.addCell(createCell("1", DEFAULT_SPAN, DEFAULT_SPAN, false));
  }

  private PdfPCell createFixedHeightCell(String text) {
    var cell = new PdfPCell(new Phrase(text, defaultFont));
    cell.setFixedHeight(cmToPoints(0.7F));
    cell.setPaddingBottom(cmToPoints(0.2F));
    cell.setHorizontalAlignment(ALIGN_CENTER);
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    return cell;
  }

  private PdfPCell createCell(String message, int rowSpan, int colSpan, boolean vertical) {
    var phrase = new Phrase(0F, message, defaultFont);

    var cell = new PdfPCell(phrase);
    cell.setRowspan(max(rowSpan, DEFAULT_SPAN));
    cell.setColspan(max(colSpan, DEFAULT_SPAN));
    cell.setVerticalAlignment(ALIGN_MIDDLE);
    cell.setHorizontalAlignment(ALIGN_CENTER);

    if (vertical) {
      cell.setRotation(VERTICAL);
      cell.setPaddingRight(cmToPoints(0.2F));
    }
    else {
      cell.setRotation(HORIZONTAL);
      cell.setPaddingBottom(cmToPoints(0.2F));
    }
    return cell;
  }

  private Font initializeFont() {
    try {
      var baseFont = createFont(properties.defaultFontLocation(), IDENTITY_H, false);
      return new Font(baseFont, DEFAULTSIZE, NORMAL);
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to initialize default font", e);  // TODO: replace with code
    }
  }
}
