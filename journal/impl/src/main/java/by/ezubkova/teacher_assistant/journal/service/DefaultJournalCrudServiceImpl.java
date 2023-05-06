package by.ezubkova.teacher_assistant.journal.service;

import static by.ezubkova.teacher_assistant.common.constant.CommonConstants.JOURNAL_MODULE_MSG_SRC;

import by.ezubkova.teacher_assistant.common.error.UserNotFoundException;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalCrudService;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.mapper.JournalMapper;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultJournalCrudServiceImpl implements JournalCrudService {

  // TODO: move to constants
  public static final String JOURNAL_FULL_NAME_TPL = "journal-full-name-tpl";

  private final JournalRepository journalRepository;
  private final UserRepository userRepository;
  private final JournalMapper mapper;

  @Qualifier(JOURNAL_MODULE_MSG_SRC)
  private final PrefixedMessageSource messageSource;

  @Override
  public void getJournal(Long id) {

  }

  @Override
  public JournalResponse getJournalByParameters(Byte classNumber,
                                                Character classLetter,
                                                Short year) {
    //    var journal = journalRepository.findByClassNameClassNumberAndClassNameClassLetterAndAcademicSemesterYear(classNumber, classLetter, year)
    //                                   .orElseThrow(() -> new JournalNotFoundException("Journal not found"));
    //    return mapper.toResponse(journal);
    return null;
  }

  @Override
  public List<JournalPreviewResponse> readJournalsAvailableForUser(String userId) {
    var teacher = findUserById(userId);
    var journals = journalRepository.findAllByTeachersContains(teacher);
    return journals.stream()
                   .map(this::createShortResponse)
                   .toList();
  }

  private User findUserById(String id) {
    return userRepository.findById(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
  }

  // TODO: move to a separate class
  private JournalPreviewResponse createShortResponse(Journal journal) {
    var journalFullName = messageSource.getMessage(JOURNAL_FULL_NAME_TPL,
                                                   journal.getClassNumber(),
                                                   journal.getClassLetter(),
                                                   journal.getAcademicSemester().getSemester());
    return new JournalPreviewResponse(journal.getId(), journalFullName);
  }
}
