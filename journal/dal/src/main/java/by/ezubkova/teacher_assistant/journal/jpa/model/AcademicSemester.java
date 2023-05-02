package by.ezubkova.teacher_assistant.journal.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(AcademicSemester.AcademicSemesterPk.class)
public class AcademicSemester {

  @Id
  private Short year;

  @Id
  private Byte semester;

  @Column(nullable = false, updatable = false)
  private LocalDate startDate;

  @Column(nullable = false, updatable = false)
  private LocalDate endDate;

  @Data
  public static class AcademicSemesterPk implements Serializable {

    @Column(nullable = false, updatable = false)
    private Short year;

    @Column(nullable = false, updatable = false)
    private Byte semester;
  }
}
