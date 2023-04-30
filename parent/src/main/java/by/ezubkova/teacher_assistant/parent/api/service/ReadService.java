package by.ezubkova.teacher_assistant.parent.api.service;

import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ReadService<RESPONSE, ID> {

  RESPONSE readEntity(ID id);

  List<RESPONSE> readAllEntities();
}
