package by.ezubkova.teacher_assistant.document_management.service;

import static by.ezubkova.teacher_assistant.document_management.constant.Measurements.*;
import static by.ezubkova.teacher_assistant.document_management.constant.Messages.*;
import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Element.ALIGN_MIDDLE;
import static com.lowagie.text.Font.DEFAULTSIZE;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.PageSize.A4;
import static com.lowagie.text.pdf.BaseFont.IDENTITY_H;
import static com.lowagie.text.pdf.BaseFont.createFont;
import static com.lowagie.text.pdf.PdfWriter.getInstance;
import static java.lang.Math.max;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Arrays.fill;
import static java.util.Locale.getDefault;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.generate;
import static java.util.stream.Stream.of;

import by.ezubkova.teacher_assistant.document_management.config.ConclusiveProgressReportProperties;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ConclusiveProgressReportGeneratorService {

  private final Object[] noArgs = new Object[0];
  private final Font defaultFont;

  private final MessageSource messages;
  private final ConclusiveProgressReportProperties properties;

  @Autowired
  public ConclusiveProgressReportGeneratorService(MessageSource messages,
                                                  ConclusiveProgressReportProperties properties) {
    this.messages = messages;
    this.properties = properties;
    this.defaultFont = initializeFont();
  }

  public void generateConclusiveProgressReport() {
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
      range(0, properties.totalTracks()).forEach(index -> createResultsTrack(table, index));

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

  private void createResultsTrack(PdfPTable table, int trackIndex) {
    createTrackRow(table, valueOf(1), false);
    createTrackRow(table, valueOf(2), false);
    createTrackRow(table, valueOf(3), false);
    createTrackRow(table, valueOf(4), false);

    var lastTrackCellText = messages.getMessage(CPR_BODY_TIME_RANGE_YEAR, noArgs, getDefault());
    createTrackRow(table, lastTrackCellText, true);
  }

  private void createTrackRow(PdfPTable table, String text, boolean lastTrackRow) {
    range(0, CONCLUSIVE_PROGRESS_REPORT_TOTAL_COLUMNS)
        .forEach(__ -> table.addCell(createDCell(text)));

    var lastAppendedRow = table.getRow(table.getRows().size() - 1);
    var timeRangeCell = lastAppendedRow.getCells()[2];
    //    timeRangeCell.getColumn().addText(new Phrase(text, defaultFont));

    if (lastTrackRow) {
      asList(lastAppendedRow.getCells()).forEach(cell -> {
        //        cell.setBorderWidthLeft(0.25F);
        //        cell.setBorderWidthRight(0.25F);
        cell.setBorderWidthBottom(1);
      });
    }
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
