package by.ezubkova.teacher_assistant.curriculum.jpa.model;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurriculumWeek {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "s_curriculum_week")
  private Long id;

  @Column(nullable = false, updatable = false)
  private Byte orderNumber;

  @OneToMany(cascade = {DETACH, REMOVE}, orphanRemoval = true)
  @JoinColumn(name = "curriculum_week_id",
              foreignKey = @ForeignKey(name = "fk_currweekday_currweek"))
  @OrderBy("dayOfWeek ASC")
  private List<CurriculumWeekday> weekdays = new ArrayList<>();
}
