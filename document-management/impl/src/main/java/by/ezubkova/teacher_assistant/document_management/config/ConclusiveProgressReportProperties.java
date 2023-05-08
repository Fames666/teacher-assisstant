package by.ezubkova.teacher_assistant.document_management.config;

import static by.ezubkova.teacher_assistant.document_management.constant.Measurements.cmToPoints;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    "application.configuration-properties.document-management.reports.conclusive-progress-report")
public record ConclusiveProgressReportProperties(
    float minDocumentMargin,
    float maxDocumentMargin,
    String defaultFontLocation,
    int totalColumns,
    int totalMarksColumns,
    int tracksPerPage
) {

  public float minDocumentMargin() {
    return cmToPoints(minDocumentMargin);
  }

  public float maxDocumentMargin() {
    return cmToPoints(maxDocumentMargin);
  }
}
