package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;
import static org.hibernate.annotations.FetchMode.JOIN;

import by.ezubkova.teacher_assistant.journal.jpa.constant.JournalRowDecoration;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class JournalRow {

  @EmbeddedId
  private JournalRowPk pkey;

  @Column(nullable = false)
  @Enumerated(STRING)
  private JournalRowDecoration decorationType;

  private Float averageMark;

  @OneToMany(orphanRemoval = true, mappedBy = "journalRow")
  private List<JournalCell> cells;

  @Embeddable
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class JournalRowPk implements Serializable {

    @MapsId
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "journal_id", foreignKey = @ForeignKey(name = "fk_journalrow_journal"))
    private Journal journal;

    @MapsId
    @ManyToOne(fetch = EAGER, optional = false)
    @Fetch(JOIN)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_journalrow_student"))
    private User student;
  }

  public JournalRow(JournalRowPk pkey, JournalRowDecoration decorationType, Float averageMark) {
    this.pkey = pkey;
    this.decorationType = decorationType;
    this.averageMark = averageMark;
  }
}
