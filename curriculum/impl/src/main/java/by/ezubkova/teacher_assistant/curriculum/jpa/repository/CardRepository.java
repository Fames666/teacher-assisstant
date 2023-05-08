package by.ezubkova.teacher_assistant.curriculum.jpa.repository;

import by.ezubkova.teacher_assistant.curriculum.jpa.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
