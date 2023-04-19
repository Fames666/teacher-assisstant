package by.ezubkova.teacher_assistant.journal.jpa.model;

import static jakarta.persistence.FetchType.EAGER;
import static org.hibernate.annotations.FetchMode.JOIN;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

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

  private Short year;

  @OneToMany(fetch = EAGER, orphanRemoval = true, mappedBy = "pkey.journal")
  @Fetch(JOIN)
  private List<JournalRow> rows;

  public Journal(Long id, Byte classNumber, Character classLetter, Short year) {
    this.id = id;
    this.classNumber = classNumber;
    this.classLetter = classLetter;
    this.year = year;
  }
}
