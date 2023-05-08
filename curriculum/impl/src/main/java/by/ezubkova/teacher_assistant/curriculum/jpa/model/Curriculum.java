package by.ezubkova.teacher_assistant.curriculum.jpa.model;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;

import jakarta.persistence.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_curriculum",
                                             columnNames = {"month", "year"}))
public class Curriculum {

  @Id
  //  @GeneratedValue(strategy = SEQUENCE, generator = "s_curriculum")
  private Long id;

  @Column(nullable = false, updatable = false)
  @Enumerated(STRING)
  private Month month;

  @Column(nullable = false, updatable = false)
  private Short year;

  @OneToMany(cascade = {DETACH, REMOVE}, orphanRemoval = true)
  @JoinColumn(name = "curriculum_id", foreignKey = @ForeignKey(name = "fk_currweek_curr"))
  @OrderBy("orderNumber ASC")
  private List<CurriculumWeek> weeks = new ArrayList<>();
}
