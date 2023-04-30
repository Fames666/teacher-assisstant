package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

import by.ezubkova.teacher_assistant.journal.constant.JournalRowDecoration;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class JournalRow {

  @EmbeddedId
  private JournalRowPk id;

  @Column(nullable = false)
  @Enumerated(STRING)
  private JournalRowDecoration decorationType;

  @Column(insertable = false)
  private Float averageMark;

  @Column(insertable = false)
  private boolean notAttested;

  @Column(insertable = false)
  private String notAttestedReason;

  @OneToMany(mappedBy = "id.row", fetch = LAZY, cascade = {PERSIST, MERGE, DETACH, REMOVE})
  private List<JournalCell> cells;

  public JournalRow(Journal journal,
                    User student,
                    JournalRowDecoration decorationType,
                    Float averageMark) {
    this.id = new JournalRowPk(journal, student);
    this.decorationType = decorationType;
    this.averageMark = averageMark;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class JournalRowPk implements Serializable {

    @Serial
    private static final long serialVersionUID = 6123311431275619179L;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "journal_id", foreignKey = @ForeignKey(name = "fk_journalrow_journal"))
    private Journal journal;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_journalrow_student"))
    private User student;
  }
}
