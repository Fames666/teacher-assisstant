package by.ezubkova.teacher_assistant.curriculum.startup;

import static by.ezubkova.teacher_assistant.curriculum.api.constant.CardType.LESSON;
import static java.time.LocalDateTime.now;
import static java.time.Month.*;
import static java.time.temporal.ChronoUnit.DAYS;

import by.ezubkova.teacher_assistant.curriculum.jpa.model.Card;
import by.ezubkova.teacher_assistant.curriculum.jpa.model.Curriculum;
import by.ezubkova.teacher_assistant.curriculum.jpa.repository.CardRepository;
import by.ezubkova.teacher_assistant.curriculum.jpa.repository.CurriculumRepository;
import by.ezubkova.teacher_assistant.user_management.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration("CurriculumTestDataSupplier")
@DependsOn("UserManagementTestDataSupplier")
@Transactional
public class TestDataSupplier {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CardRepository cardRepository;
  @Autowired
  private CurriculumRepository curriculumRepository;

  @PostConstruct
  public void initDb() {
    var curriculums = CurriculumTestDataSupplier.createTestCurriculums();
    curriculumRepository.saveAllAndFlush(curriculums);

    var card = createTestCard();
    cardRepository.saveAndFlush(card);
  }

  private Card createTestCard() {
    var author = userRepository.findAll().iterator().next();
    return new Card(null,
                    "Some lesson name",
                    author,
                    "CREATED",
                    now(),
                    now(),
                    now().plus(1, DAYS),
                    LESSON,
                    new HashSet<>());
  }

  protected static class CurriculumTestDataSupplier {

    private static final Set<Long> IDS = Set.of(1L, 2L, 3L, 4L, 5L, 6L);
    private static final List<Month> MONTHS = List.of(JANUARY, FEBRUARY, MARCH, APRIL,
                                                      JANUARY, FEBRUARY);
    private static final List<Short> YEARS = Stream.of(2022, 2022, 2022, 2022,
                                                       2023, 2023)
        .map(Integer::shortValue)
        .toList();

    private static List<Curriculum> createTestCurriculums() {
      var months = MONTHS.iterator();
      var years = YEARS.iterator();
      var curriculums = new ArrayList<Curriculum>(IDS.size());

      for (var id : IDS) {
        curriculums.add(new Curriculum(id, months.next(), years.next(), new ArrayList<>()));
      }
      return curriculums;
    }
  }
}
