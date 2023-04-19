package by.ezubkova.teacher_assistant.journal.jpa.model;

import static by.ezubkova.teacher_assistant.journal.api.constant.JournalConstants.UNCERTAINTY_REASON_LENGTH;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

import by.ezubkova.teacher_assistant.journal.jpa.constant.JournalCellHighlight;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class JournalCell {

  @Id
  @MapsId
  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumns({
      @JoinColumn(name = "row_id", foreignKey = @ForeignKey(name = "fk_journalcell_journalrow")),
      @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_journalcell_student"))
  })
  private JournalRow journalRow;

  @Column(nullable = false, updatable = false)
  private LocalDate date;

  @Column(nullable = false)
  @Enumerated(STRING)
  private JournalCellHighlight highlightType;

  private Byte mark;

  private Boolean uncertain;

  @Column(length = UNCERTAINTY_REASON_LENGTH)
  private String uncertaintyReason;

  public JournalCell(LocalDate date,
                     JournalCellHighlight highlightType,
                     Byte mark,
                     Boolean uncertain,
                     String uncertaintyReason) {
    this.date = date;
    this.highlightType = highlightType;
    this.mark = mark;
    this.uncertain = uncertain;
    this.uncertaintyReason = uncertaintyReason;
  }
}
