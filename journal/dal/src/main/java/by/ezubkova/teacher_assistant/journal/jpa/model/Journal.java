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
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
//@Table(uniqueConstraints = @UniqueConstraint(name = "uk_journal",
//                                             columnNames = {
//                                                 "classNumber",
//                                                 "classLetter",
//                                                 "year",
//                                                 "semester"
//                                             }))
public class Journal {

  @Id
  //  @GeneratedValue(strategy = SEQUENCE, generator = "s_journal")
  private Long id;

  @Embedded
  private ClassName className;

  @ManyToOne(fetch = LAZY, optional = false)
  @JoinColumns(value =
                   {@JoinColumn(name = "year", referencedColumnName = "year"),
                       @JoinColumn(name = "semester", referencedColumnName = "semester")},
               foreignKey = @ForeignKey(name = "fk_journal_academicsemester"))
  private AcademicSemester academicSemester;

  @OneToMany(mappedBy = "id.journal", fetch = LAZY, cascade = {PERSIST, MERGE, DETACH, REMOVE})
  private List<JournalRow> rows;

  @ManyToMany
  @JoinTable(name = "journal_teacher",
             joinColumns = @JoinColumn(name = "journal_id"),
             inverseJoinColumns = @JoinColumn(name = "teacher_id"),
             foreignKey = @ForeignKey(name = "fk_journaluser_journal"),
             inverseForeignKey = @ForeignKey(name = "fk_teacher_journal"))
  private List<User> teachers;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "lead_teacher_id", foreignKey = @ForeignKey(name = "fk_journal_leadteacher"))
  private User leadTeacher;

  @Embeddable
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ClassName {

    @Column(nullable = false, updatable = false)
    private Character classLetter;

    @Column(nullable = false, updatable = false)
    private Byte classNumber;

    public String retrieveFullClassName() {
      return format("%d%s", getClassNumber(), getClassLetter()).toUpperCase();
    }
  }
}
