package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

  Optional<Journal> findByClassNumberAndClassLetterAndYear(Byte classNumber,
                                                           Character classLetter,
                                                           Short year);
}
