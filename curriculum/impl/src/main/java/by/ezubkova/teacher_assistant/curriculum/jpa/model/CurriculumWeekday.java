package by.ezubkova.teacher_assistant.curriculum.jpa.model;

import static jakarta.persistence.EnumType.ORDINAL;
import static jakarta.persistence.GenerationType.SEQUENCE;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurriculumWeekday {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "s_curriculum_weekday")
  private Long id;

  @Column(nullable = false, updatable = false)
  @Enumerated(ORDINAL)
  private DayOfWeek dayOfWeek;

  @Column(nullable = false, updatable = false)
  private LocalDate date;

  @OneToMany()
  @JoinColumn(name = "curriculum_weekday_id",
              foreignKey = @ForeignKey(name = "fk_card_currweekday"))
  @OrderBy("startTime ASC")
  private List<Card> cards = new ArrayList<>();
}
