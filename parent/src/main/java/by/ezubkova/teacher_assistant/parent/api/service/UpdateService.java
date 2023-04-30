package by.ezubkova.teacher_assistant.parent.api.service;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateService<REQUEST, RESPONSE, ID> {

  RESPONSE updateEntity(@NotNull ID id, REQUEST updateRequest);

  RESPONSE patchEntity(@NotNull ID id, Map<String, Object> updatableProperties);
}
