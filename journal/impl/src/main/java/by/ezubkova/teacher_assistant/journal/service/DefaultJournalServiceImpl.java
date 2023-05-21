package by.ezubkova.teacher_assistant.journal.service;

import static by.ezubkova.teacher_assistant.common.util.CollectionUtils.*;
import static by.ezubkova.teacher_assistant.journal.api.constant.JournalConstants.JOURNAL_MODULE_MSG_SRC;
import static by.ezubkova.teacher_assistant.journal.api.constant.StudentPreviewMarkerType.*;
import static by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight.ATTENTION;
import static by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight.NONE;

import by.ezubkova.teacher_assistant.common.error.UserNotFoundException;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import by.ezubkova.teacher_assistant.common.util.CommonUtils;
import by.ezubkova.teacher_assistant.journal.api.model.JournalCellResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalRowResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse;
import by.ezubkova.teacher_assistant.journal.api.model.JournalUserPreviewResponse.StudentPreviewMarker;
import by.ezubkova.teacher_assistant.journal.api.service.JournalService;
import by.ezubkova.teacher_assistant.journal.constant.JournalCellHighlight;
import by.ezubkova.teacher_assistant.journal.error.JournalNotFoundException;
import by.ezubkova.teacher_assistant.journal.jpa.model.Journal;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalCell;
import by.ezubkova.teacher_assistant.journal.jpa.model.JournalRow;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRowRepository;
import by.ezubkova.teacher_assistant.journal.mapper.JournalCellMapper;
import by.ezubkova.teacher_assistant.journal.mapper.JournalMapper;
import by.ezubkova.teacher_assistant.journal.mapper.JournalRowMapper;
import by.ezubkova.teacher_assistant.user_management.api.service.UserFacade;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
  private final JournalRowRepository journalRowRepository;
  private final UserRepository userRepository;
  private final UserFacade userFacade;
  private final JournalMapper journalMapper;
  private final JournalRowMapper journalRowMapper;
  private final JournalCellMapper journalCellMapper;

  @Qualifier(JOURNAL_MODULE_MSG_SRC)
  private final PrefixedMessageSource messageSource;

  @Override
  public JournalResponse getJournal(Long journalId) {
    var journal = findJournalWithStudents(journalId);
    var leadTeacher = findUserById(journal.getLeadTeacher().getId());
    journal.setLeadTeacher(leadTeacher);

    var response = journalMapper.toResponse(journal);
    countRemarks(response);
    return response;
  }

  @Override
  public JournalRowResponse putMark(Long journalId, String studentId, LocalDate date, Byte mark) {
    var row = findJournalRowByIdWithCell(journalId, studentId);
    var cell = filterCellByDate(row, date);
    cell.setMark(mark);
    recalculateAverageMark(row);
    return createJournalRowResponseWithSpecificCell(row, cell);
  }

  @Override
  public JournalRowResponse toggleRemark(Long journalId, String studentId, LocalDate date) {
    var row = findJournalRowByIdWithCell(journalId, studentId);
    var cell = filterCellByDate(row, date);

    cell.setUncertain(!cell.getUncertain());
    if (cell.getUncertain()) {
      cell.setHighlightType(ATTENTION);
    } else {
      cell.setHighlightType(NONE);
    }
    return createJournalRowResponseWithSpecificCell(row, cell);
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
    var journal = findJournalWithStudents(journalId);
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
        findAll(studentRow.getCells(), JournalCell::getUncertain, CommonUtils::isTrue).size();
    var marker = online ? ONLINE : OFFLINE;
    marker = remarksAmount > 0 ? HAS_REMARKS : marker;
    return new StudentPreviewMarker(marker, remarksAmount);
  }

  private void countRemarks(JournalResponse response) {
    response.getRows().forEach(row -> {
      var cellsWithRemarks = filter(row.getCells(), JournalCellResponse::getUncertain);
      row.setRemarksAmount(cellsWithRemarks.size());
    });
  }

  private JournalCell filterCellByDate(JournalRow row, LocalDate date) {
    var filteredCells = filter(row.getCells(),
                               cell -> cell.getId().getDate().equals(date));
    return first(filteredCells);
  }

  private JournalRowResponse createJournalRowResponseWithSpecificCell(JournalRow row,
                                                                      JournalCell cell) {
    var rowResponse = createJournalRowResponseWithoutCells(row);
    rowResponse.getCells().add(journalCellMapper.toResponse(cell));
    return rowResponse;
  }

  private JournalRowResponse createJournalRowResponseWithoutCells(JournalRow row) {
    var cells = row.getCells();
    row.setCells(null);
    var rowResponse = journalRowMapper.toResponse(row);
    row.setCells(cells);
    return rowResponse;
  }

  private void recalculateAverageMark(JournalRow row) {
    var cells = row.getCells();
    var cellsWithMarks = findAll(cells, JournalCell::getMark, Objects::nonNull);
    if (isEmpty(cellsWithMarks)) {
      row.setAverageMark(null);
      return;
    }
    var marksSum = cellsWithMarks.stream()
                                 .map(mark -> (int) mark)
                                 .reduce(0, Integer::sum);
    double recalculatedAverageMark = marksSum * 1.0 / cellsWithMarks.size();
    row.setAverageMark((float) recalculatedAverageMark);
  }

  private Journal findJournalWithStudents(Long id) {
    return journalRepository.findByIdWithStudents(id)
                            .orElseThrow(() -> JournalNotFoundException.byId(id));
  }

  private JournalRow findJournalRowByIdWithCell(Long journalId, String studentId) {
    return journalRowRepository.findById(journalId, studentId)
                               .orElseThrow(() -> new EntityNotFoundException("No Journal Row found with ID = " + journalId));
  }

  private User findUserById(String id) {
    return userRepository.findById(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
  }
}
