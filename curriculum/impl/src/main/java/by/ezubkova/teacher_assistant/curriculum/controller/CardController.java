package by.ezubkova.teacher_assistant.curriculum.controller;

import static java.lang.Long.valueOf;

import by.ezubkova.teacher_assistant.curriculum.api.model.CardCreateRequest;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardResponse;
import by.ezubkova.teacher_assistant.curriculum.api.service.CardService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

  private final CardService service;

  @GetMapping("/{cardId}")
  public String getCard(@PathVariable String cardId) {
    Long numberFormatId = valueOf(cardId);
    return null;
  }

  @PutMapping("/create")
  public CardResponse createCard(@RequestBody CardCreateRequest request) {
    return service.createCard(request);
  }

  @PostMapping("/{cardId}/update")
  public CardResponse updateCard(@PathVariable Long cardId,
                                 @RequestBody Map<String, String> request) {
    return null;
  }

  @DeleteMapping("/{cardId}/delete")
  public String deleteCard(@PathVariable Long cardId) {
    return null; // return ID;
  }

  @PostMapping("/{cardId}/add-description")
  public CardResponse addCardDescription(@PathVariable Long cardId, String description) {
    return null;
  }
}
