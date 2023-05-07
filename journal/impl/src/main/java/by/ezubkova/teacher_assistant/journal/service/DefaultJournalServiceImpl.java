package by.ezubkova.teacher_assistant.journal.service;

import static by.ezubkova.teacher_assistant.common.util.CollectionUtils.findAll;
import static by.ezubkova.teacher_assistant.journal.api.constant.JournalConstants.JOURNAL_MODULE_MSG_SRC;
import static by.ezubkova.teacher_assistant.journal.api.constant.StudentPreviewMarkerType.*;

import by.ezubkova.teacher_assistant.common.error.UserNotFoundException;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import by.ezubkova.teacher_assistant.common.util.ObjectUtils;
import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse.StudentPreviewMarker;
import by.ezubkova.teacher_assistant.journal.api.service.JournalService;
import by.ezubkova.teacher_assistant.journal.error.JournalNotFoundException;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.mapper.JournalMapper;
import by.ezubkova.teacher_assistant.user_management.api.service.UserFacade;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultJournalServiceImpl implements JournalService {

  // TODO: move to constants
  public static final String JOURNAL_FULL_NAME_TPL = "journal-full-name-tpl";

  private final JournalRepository journalRepository;
  private final UserRepository userRepository;
  private final UserFacade userFacade;
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
  public List<JournalPreviewResponse> findJournalsAvailableForUser(String userId) {
    var teacher = findUserById(userId);
    var journals = journalRepository.findAllByTeachersContains(teacher);
    return journals.stream()
                   .map(this::createJournalPreviewResponse)
                   .toList();
  }

  @Override
  public List<JournalUserPreviewResponse> createJournalUsersPreviews(Long journalId) {
    List<JournalUserPreviewResponse> users = new ArrayList<>();
    var journal = journalRepository.findByIdWithStudents(journalId)
                                   .orElseThrow(() -> JournalNotFoundException.byId(journalId));
    var journalRows = journal.getRows();
    journalRows.stream()
               .map(row -> createUserPreviewResponse(row.getId().getStudent(), row))
               .forEach(users::add);
    journalRepository.findByIdWithTeachers(journalId)
                     .ifPresent(entity -> entity.getTeachers().stream()
                                                .map(this::createUserPreviewResponse)
                                                .forEach(users::add));
    return users;
  }

  private JournalPreviewResponse createJournalPreviewResponse(Journal journal) {
    var journalFullName = messageSource.getMessage(JOURNAL_FULL_NAME_TPL,
                                                   journal.getClassNumber(),
                                                   journal.getClassLetter(),
                                                   journal.getAcademicSemester().getSemester());
    return new JournalPreviewResponse(journal.getId(), journalFullName);
  }

  private JournalUserPreviewResponse createUserPreviewResponse(User journalUser) {
    return createUserPreviewResponse(journalUser, null);
  }

  private JournalUserPreviewResponse createUserPreviewResponse(User journalUser,
                                                               JournalRow studentRow) {
    var userData = journalUser.getUserData();
    var lastActivity = journalUser.getLastActivity();
    var fullName = userFacade.formatFullName(userData.getFirstName(),
                                             userData.getSecondName(),
                                             userData.getThirdName());
    var lastOnline = userFacade.formatLastOnlineStatus(lastActivity.getOnline(), lastActivity.getLastOnlineAt());
    var marker = createUserPreviewMarker(lastActivity.getOnline(), studentRow);
    return new JournalUserPreviewResponse(journalUser.getId(), fullName, lastOnline, marker);
  }

  private StudentPreviewMarker createUserPreviewMarker(Boolean online, JournalRow studentRow) {
    var remarksAmount = studentRow == null ? 0 :
        findAll(studentRow.getCells(), JournalCell::getUncertain, ObjectUtils::isTrue).size();
    var marker = online ? ONLINE : OFFLINE;
    marker = remarksAmount > 0 ? HAS_REMARKS : marker;
    return new StudentPreviewMarker(marker, remarksAmount);
  }

  private User findUserById(String id) {
    return userRepository.findById(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
  }
}
