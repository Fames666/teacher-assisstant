package by.ezubkova.teacher_assistant.user_management.startup;

import static java.util.stream.Stream.generate;

import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.model.UserData;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("generate-test-data")
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
  private static final Set<String> USERNAMES = Set.of("anna22", "john13", "ivan35");
  private static final List<String> PASSWORDS = generate(() -> "test").limit(NAMES.size()).toList();

  @PostConstruct
  public void initDb() {
    var users = createTestUsers();
    userRepository.saveAllAndFlush(users);
  }

  private List<User> createTestUsers() {
    var names = NAMES.iterator();
    var surnames = SURNAMES.iterator();
    var usernames = USERNAMES.iterator();
    var passwords = PASSWORDS.iterator();
    var users = new ArrayList<User>(IDS.size());

    for (var id : IDS) {
      var userData = new UserData(names.next(), surnames.next(), null);
      var user = new User(id, usernames.next(), passwords.next(), true, true, true, true, userData);
      userData.setUser(user);
      users.add(user);
    }
    return users;
  }
}
