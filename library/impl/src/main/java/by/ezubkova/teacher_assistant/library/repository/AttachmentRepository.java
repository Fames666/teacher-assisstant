package by.ezubkova.teacher_assistant.library.repository;

import by.ezubkova.teacher_assistant.library.model.AttachmentEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntry, Long> {
}
