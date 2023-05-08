package by.ezubkova.teacher_assistant.curriculum.jpa.repository;

import by.ezubkova.teacher_assistant.curriculum.jpa.model.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
