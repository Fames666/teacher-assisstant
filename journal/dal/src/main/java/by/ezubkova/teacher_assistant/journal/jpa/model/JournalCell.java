package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

import by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class JournalCell {

  @EmbeddedId
  private JournalCellPk id;

  //  @Column(nullable = false)
  @Enumerated(STRING)
  private JournalCellHighlight highlightType;

  //  @Column(insertable = false)
  private Byte mark;

  // TODO: rename to "remarked"
  //  @Column(insertable = false)
  private Boolean uncertain;

  //  @Column(length = UNCERTAINTY_REASON_LENGTH, insertable = false)
  private String uncertaintyReason;

  public JournalCell(LocalDate date,
                     JournalCellHighlight highlightType,
                     Byte mark,
                     Boolean uncertain,
                     String uncertaintyReason) {
    this.id = new JournalCellPk();
    this.id.setDate(date);

    this.highlightType = highlightType;
    this.mark = mark;
    this.uncertain = uncertain;
    this.uncertaintyReason = uncertaintyReason;
  }

  @Getter
  @Setter
  @Embeddable
  public static class JournalCellPk implements Serializable {

    @Serial
    private static final long serialVersionUID = 8578148734239307004L;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumns(value = {@JoinColumn(name = "row_id"), @JoinColumn(name = "student_id")},
                 foreignKey = @ForeignKey(name = "fk_journalcell_journalrow"))
    private JournalRow row;

    @Column(nullable = false, updatable = false)
    private LocalDate date;
  }
}
