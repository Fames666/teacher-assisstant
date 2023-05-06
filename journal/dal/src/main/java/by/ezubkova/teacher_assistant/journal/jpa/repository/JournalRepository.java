package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

  //  Optional<Journal> findByClassNameClassNumberAndClassNameClassLetterAndAcademicSemesterYear(Byte classNumber,
  //                                                                                             Character classLetter,
  //                                                                                             Short year);

  // TODO: is query necessary?
  //  @Query("""
  //         FROM Journal jrl JOIN jrl.teachers tch
  //                          JOIN jrl.academicSemester
  //         WHERE tch.id = :userId
  //         """)
  @EntityGraph(attributePaths = {"teachers", "academicSemester"})
  List<Journal> findAllByTeachersContains(User teacher);
}
