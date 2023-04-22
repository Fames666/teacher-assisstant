package by.ezubkova.teacher_assistant.journal.jpa.repository;

import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow.JournalRowPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRowRepository extends JpaRepository<JournalRow, JournalRowPk> {
}
