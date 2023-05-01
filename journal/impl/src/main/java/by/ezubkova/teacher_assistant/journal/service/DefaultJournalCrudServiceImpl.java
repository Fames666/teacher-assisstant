package by.ezubkova.teacher_assistant.journal.service;

import by.ezubkova.teacher_assistant.common.error.UserNotFoundException;
import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalCrudService;
import by.ezubkova.teacher_assistant.journal.error.JournalNotFoundException;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.mapper.JournalMapper;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultJournalCrudServiceImpl implements JournalCrudService {

  private final JournalRepository journalRepository;
  private final UserRepository userRepository;
  private final JournalMapper mapper;

  @Override
  public void getJournal(Long id) {

  }

  @Override
  public JournalResponse getJournalByParameters(Byte classNumber,
                                                Character classLetter,
                                                Short year) {
    var journal = journalRepository.findByClassNumberAndClassLetterAndYear(classNumber, classLetter, year)
                                   .orElseThrow(() -> new JournalNotFoundException("Journal not found"));
    return mapper.toResponse(journal);
  }

  @Override
  public List<JournalResponse> readJournalsAvailableForUser(String userId) {
    var user = findUserById(userId);
    var journals = journalRepository.findAllByTeacherContains(user);
    return journals.stream()
                   .map(mapper::toResponse)
                   .toList();
  }

  private User findUserById(String id) {
    return userRepository.findById(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
  }
}
