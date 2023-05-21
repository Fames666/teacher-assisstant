package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow.JournalRowPk;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRowRepository extends JpaRepository<JournalRow, JournalRowPk> {

  // TODO: validation
  @Query("""
      SELECT row FROM JournalRow row
      JOIN FETCH row.cells cell
      WHERE row.id.journal.id = :journalId
        AND row.id.student.id = :studentId
      """)
  Optional<JournalRow> findById(Long journalId, String studentId);
}
