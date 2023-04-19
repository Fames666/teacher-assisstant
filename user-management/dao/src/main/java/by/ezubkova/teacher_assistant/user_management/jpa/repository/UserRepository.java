package by.ezubkova.teacher_assistant.user_management.jpa.repository;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Getter
//@Setter
@Repository
public interface UserRepository extends JpaRepository<User, String> {

  //  @PersistenceContext
  //  private EntityManager entityManager;

  public abstract Optional<User> findUserByUsername(String username);
}
