package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

  @EntityGraph(attributePaths = {"teachers", "academicSemester"})
  List<Journal> findAllByTeachersContains(User teacher);

  @Query("""
      FROM Journal jrl
        JOIN jrl.rows jrr
        JOIN jrr.cells
        JOIN jrr.id.student stu
        JOIN stu.userData
      WHERE jrl.id = :journalId
      """)
  Optional<Journal> findByIdWithStudents(@NotNull Long journalId);

  // TODO: add leadTeacher
  @Query("""
      FROM Journal jrl
        JOIN jrl.teachers tch
        JOIN tch.userData
      WHERE  jrl.id = :journalId
      """)
  Optional<Journal> findByIdWithTeachers(@NotNull Long journalId);

  @Query("""
      SELECT journal FROM Journal journal
        JOIN journal.rows rows
        JOIN rows.cells
        JOIN FETCH journal.academicSemester semester
        JOIN FETCH journal.leadTeacher
      WHERE journal.classNumber = :classNumber
        AND journal.classLetter = :classLetter
        AND semester.year = :year
      """)
  List<Journal> findAllByClassAndYear(Byte classNumber, Character classLetter, Short year);
}
