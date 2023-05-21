package by.ezubkova.teacher_assistant.user_management.jpa.repository;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  // TODO: apply specifications?
  // TODO: validation
  // TODO: use projection
  @Query("SELECT user FROM User user JOIN user.userData WHERE user.id = :userId")
  Optional<User> findUserByIdWithUserData(Long userId);
}
