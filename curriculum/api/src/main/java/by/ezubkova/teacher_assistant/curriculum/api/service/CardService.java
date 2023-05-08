package by.ezubkova.teacher_assistant.curriculum.api.service;

import by.ezubkova.teacher_assistant.curriculum.api.model.CardCreateRequest;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardResponse;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CardService {

  CardResponse createCard(@Valid CardCreateRequest request);
}
