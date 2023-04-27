package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static java.lang.String.format;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_journal",
                                             columnNames = {"classNumber", "classLetter", "year"}))
public class Journal {

  @Id
  //  @GeneratedValue(strategy = SEQUENCE, generator = "s_journal")
  private Long id;

  @Column(nullable = false, updatable = false)
  private Byte classNumber;

  @Column(nullable = false, updatable = false)
  private Character classLetter;

  @Column(nullable = false, updatable = false)
  private Short year;

  //  @Column(nullable = false, updatable = false)
  private Byte semester;

  @OneToMany(mappedBy = "id.journal", fetch = LAZY, cascade = {PERSIST, MERGE, DETACH, REMOVE})
  private List<JournalRow> rows;

  @ManyToOne(optional = false, fetch = LAZY)
  @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_journal_teacher"))
  private User teacher;

  public Journal(Long id, Byte classNumber, Character classLetter, Short year) {
    this.id = id;
    this.classNumber = classNumber;
    this.classLetter = classLetter;
    this.year = year;
  }

  public String composeFullClassName() {
    return format("%d%s", getClassNumber(), getClassLetter());
  }
}
