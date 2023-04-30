package by.ezubkova.teacher_assistant.parent.api.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CreateService<REQUEST, RESPONSE> {

  RESPONSE createEntity(@Valid REQUEST createRequest);
}
