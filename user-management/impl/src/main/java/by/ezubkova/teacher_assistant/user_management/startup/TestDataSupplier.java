package by.ezubkova.teacher_assistant.user_management.startup;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration("UserManagementTestDataSupplier")
@Transactional
public class TestDataSupplier {

  @Autowired
  private UserRepository userRepository;

  private static final Set<String> IDS = Set.of(
      "42b70963-0db7-4bc4-8f0e-54b8169cc34e",
      "fd36529c-3cce-4442-9015-2048e336d93c",
      "7cad1065-d757-48ed-95c3-2262bdb41dcb"
  );

  private static final Set<String> NAMES = Set.of("Anna", "John", "Ivan");

  private static final Set<String> SURNAMES = Set.of("Cringe", "Book", "Lorry");

  @PostConstruct
  public void initDb() {
    var users = createTestUsers();
    userRepository.saveAllAndFlush(users);
  }

  private List<User> createTestUsers() {
    var names = NAMES.iterator();
    var surnames = SURNAMES.iterator();
    var users = new ArrayList<User>(IDS.size());

    for (var id : IDS) {
      users.add(new User(id,
                         names.next(),
                         surnames.next(),
                         true,
                         true,
                         true,
                         true));
    }
    return users;
  }
}
