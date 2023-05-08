package by.ezubkova.teacher_assistant.user_management.jpa.repository;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  
}
