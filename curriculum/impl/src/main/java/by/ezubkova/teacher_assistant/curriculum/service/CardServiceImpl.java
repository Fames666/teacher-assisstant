package by.ezubkova.teacher_assistant.curriculum.service;

import by.ezubkova.teacher_assistant.common.error.UserNotFoundException;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardCreateRequest;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardResponse;
import by.ezubkova.teacher_assistant.curriculum.api.service.CardService;
import by.ezubkova.teacher_assistant.curriculum.jpa.repository.CardRepository;
import by.ezubkova.teacher_assistant.curriculum.mapper.CardMapper;
import by.ezubkova.teacher_assistant.user_management.jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

  private final CardMapper mapper;
  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  public CardResponse createCard(CardCreateRequest request) {
    var authorId = request.getAuthorId();
    var author = userRepository.findById(authorId);
    if (author.isEmpty()) {
      throw new UserNotFoundException(authorId);
    }

    var entity = mapper.fromCreateRequest(request);
    entity.setAuthor(author.get());
    return mapper.toResponse(cardRepository.save(entity));
  }
}
