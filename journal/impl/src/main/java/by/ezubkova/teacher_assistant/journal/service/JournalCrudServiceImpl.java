package by.ezubkova.teacher_assistant.journal.service;

import by.ezubkova.teacher_assistant.journal.api.model.JournalResponse;
import by.ezubkova.teacher_assistant.journal.api.service.JournalCrudService;
import by.ezubkova.teacher_assistant.journal.error.JournalNotFoundException;
import by.ezubkova.teacher_assistant.journal.jpa.repository.JournalRepository;
import by.ezubkova.teacher_assistant.journal.mapper.JournalMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalCrudServiceImpl implements JournalCrudService {

  private final JournalRepository repository;
  private final JournalMapper mapper;

  @Override
  public void getJournal(Long id) {

  }

  @Override
  public JournalResponse getJournalByParameters(Byte classNumber,
                                                Character classLetter,
                                                Short year) {
    var journal = repository.findByClassNumberAndClassLetterAndYear(classNumber, classLetter, year)
        .orElseThrow(() -> new JournalNotFoundException("Journal not found"));
    return mapper.toResponse(journal);
  }
}
