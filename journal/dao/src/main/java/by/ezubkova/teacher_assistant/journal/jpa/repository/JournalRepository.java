package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

  Optional<Journal> findByClassNumberAndClassLetterAndYear(Byte classNumber,
                                                           Character classLetter,
                                                           Short year);

  @EntityGraph(attributePaths = {
      "teacher",
      "rows",
      "rows.cells"
  })
  List<Journal> findAllByTeacher(User teacher);
}
