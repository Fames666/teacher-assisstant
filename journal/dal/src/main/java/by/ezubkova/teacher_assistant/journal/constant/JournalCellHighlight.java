package by.ezubkova.teacher_assistant.journal.constant;

import lombok.Getter;

@Getter
public enum JournalCellHighlight {

  NONE("none"),
  ABSENCE_WITHOUT_REASON("absence-without-reason"),
  ABSENCE_WITH_REASON("absence-with-reason"),
  ATTENTION("attention");

  final String value;

  JournalCellHighlight(String value) {
    this.value = value;
  }
}
