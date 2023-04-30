package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static java.lang.String.format;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_journal",
                                             columnNames = {
                                                 "className.classNumber",
                                                 "className.classLetter",
                                                 "activePeriod.year",
                                                 "activePeriod.semester"
                                             }))
public class Journal {

  @Id
  //  @GeneratedValue(strategy = SEQUENCE, generator = "s_journal")
  private Long id;

  @Embedded
  private ClassName className;

  @Embedded
  private ActivePeriod activePeriod;

  @OneToMany(mappedBy = "id.journal", fetch = LAZY, cascade = {PERSIST, MERGE, DETACH, REMOVE})
  private List<JournalRow> rows;

  @ManyToMany
  @JoinTable(name = "journal_teacher",
             joinColumns = @JoinColumn(name = "journal_id"),
             inverseJoinColumns = @JoinColumn(name = "teacher_id"),
             foreignKey = @ForeignKey(name = "fk_journal_teacher"),
             inverseForeignKey = @ForeignKey(name = "fk_teacher_journal"))
  private List<User> teacher;

  public Journal(Long id, Byte classNumber, Character classLetter, Short year, Byte semester) {
    this.id = id;
    this.className = new ClassName(classNumber, classLetter);
    this.activePeriod = new ActivePeriod(year, semester);
  }

  @Embeddable
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ClassName {

    @Column(nullable = false, updatable = false)
    private Byte classNumber;

    @Column(nullable = false, updatable = false)
    private Character classLetter;

    public String getFullClassName() {
      return format("%d%s", getClassNumber(), getClassLetter()).toUpperCase();
    }
  }

  @Embeddable
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class ActivePeriod {

    @Column(nullable = false, updatable = false)
    private Short year;

    @Column(nullable = false, updatable = false)
    private Byte semester;
  }
}
