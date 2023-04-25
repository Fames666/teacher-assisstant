package by.ezubkova.teacher_assistant.document_management.service;

import static by.ezubkova.teacher_assistant.document_management.constant.Measurements.*;
import static by.ezubkova.teacher_assistant.document_management.constant.Messages.*;
import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Font.*;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.pdf.PdfWriter.getInstance;
import static java.lang.Math.max;
import static java.util.Arrays.fill;
import static java.util.Locale.getDefault;
import static java.util.stream.Stream.generate;
import static java.util.stream.Stream.of;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConclusiveProgressReportGeneratorService {

  public static final float PAGE_MIN_MARGIN = cmToPoints(0.5F);

  private final Font defaultFont = new Font(TIMES_ROMAN, DEFAULTSIZE, NORMAL);

  private final MessageSource messages;

  public void generateConclusiveProgressReport() {
    try (var document = new Document(A4.rotate(), PAGE_MIN_MARGIN, PAGE_MIN_MARGIN, cmToPoints(2F), PAGE_MIN_MARGIN)) {
      getInstance(document, new FileOutputStream("tables.pdf"));
      document.open();

      var table = new PdfPTable(CONCLUSIVE_PROGRESS_REPORT_TOTAL_COLUMNS);
      applyConstraintsToReportTable(table, document);
      createHeader(table);
      createResultsTrack(table);

      document.add(table);
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void applyConstraintsToReportTable(PdfPTable table, Document document) {
    var totalWidth = document.getPageSize().getWidth() - 2 * PAGE_MIN_MARGIN;
    var columnsAmount = CONCLUSIVE_PROGRESS_REPORT_TOTAL_COLUMNS;
    var relativeColumnsWidths = new float[columnsAmount];
    fill(relativeColumnsWidths, 1F);
    relativeColumnsWidths[columnsAmount - 1] =
        relativeColumnsWidths[columnsAmount - 2] = 2.5F;

    table.setHorizontalAlignment(ALIGN_CENTER);
    table.setTotalWidth(totalWidth);
    table.setWidths(relativeColumnsWidths);
    table.setLockedWidth(true);

    var defaultCell = table.getDefaultCell();
    defaultCell.setFixedHeight(cmToPoints(0.9F));
    defaultCell.setVerticalAlignment(ALIGN_MIDDLE);
//    defaultCell.setHorizontalAlignment(ALIGN_CENTER);
//    defaultCell.setPadding(0);
//    defaultCell.setBorderWidth(0);
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
        .limit(CONCLUSIVE_PROGRESS_REPORT_MARKS_COLUMNS)
        .map(value -> createCell(value.toString(), 1, 1, false))
        .forEach(table::addCell);
  }

  private PdfPCell createCellWithText(String msgCode, int rowSpan, int colSpan, boolean vertical) {
    var message = messages.getMessage(msgCode, new Object[0], getDefault());
    return createCell(message, rowSpan, colSpan, vertical);
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

  private void applyRowFixedHeight(PdfPRow row, float height) {
    of(row.getCells())
        .filter(Objects::nonNull)
        .forEach(cell -> cell.setFixedHeight(height));
    row.calculateHeights();
  }

  private void createResultsTrack(PdfPTable table) {
    createTrackRow(table, false, 1);
    createTrackRow(table, false, 2);
    createTrackRow(table, false, 3);
    createTrackRow(table, false, 4);
    createTrackRow(table, true, null);
  }

  private void createTrackRow(PdfPTable table, boolean yearResultsRow, Integer ordinal) {
    table.addCell(EMPTY);
    table.completeRow();

    var lastAppendedRow = table.getRow(table.getRows().size() - 1);
    var timeRangeCell = lastAppendedRow.getCells()[2];

    if (yearResultsRow) {
      var cellText = messages.getMessage(CPR_BODY_TIME_RANGE_YEAR, new Object[0], getDefault());
      timeRangeCell.setPhrase(new Phrase(cellText, defaultFont));
//      asList(lastAppendedRow.getCells()).forEach(cell -> cell.setBorderWidthBottom(2F));
    }
    else {
      timeRangeCell.setPhrase(new Phrase(ordinal.toString(), defaultFont));
    }
  }
}
