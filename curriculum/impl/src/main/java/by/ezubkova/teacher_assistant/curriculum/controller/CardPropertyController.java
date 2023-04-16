package by.ezubkova.teacher_assistant.curriculum.controller;

import by.ezubkova.teacher_assistant.curriculum.api.model.CardPropertyCreateRequest;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardPropertyController {

  @PutMapping("/{cardId}/property/add")
  public CardResponse addCardProperty(@PathVariable Long cardId,
                                      @RequestBody CardPropertyCreateRequest request) {
    return null;
  }

  @PostMapping("/{cardId}/property/update")
  public CardResponse updateCardProperty(@PathVariable Long cardId,
                                         String propertyName,
                                         @RequestBody byte[] value) {
    return null;
  }

  @PostMapping("/{cardId}/property/delete")
  public CardResponse deleteCardProperty(@PathVariable Long cardId, String propertyName) {
    return null;
  }
}
