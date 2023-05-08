package by.ezubkova.teacher_assistant.curriculum.mapper;

import static by.ezubkova.teacher_assistant.curriculum.api.constant.CardConstraints.CARD_ID_MIN_LENGHT;
import static by.ezubkova.teacher_assistant.curriculum.api.constant.CardConstraints.CardPerformableActions.CARD_ACTION_CREATED;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.stream;

import by.ezubkova.teacher_assistant.curriculum.api.constant.CardType;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardCreateRequest;
import by.ezubkova.teacher_assistant.curriculum.api.model.CardResponse;
import by.ezubkova.teacher_assistant.curriculum.jpa.model.Card;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(imports = {LocalDateTime.class})
public interface CardMapper {

  String METHOD_MAP_REQUEST_CARD_TYPE = "mapRequestCardType";
  String METHOD_MAP_RESPONSE_ID = "mapResponseId";
  String METHOD_MAP_AUTHOR_FULL_NAME = "mapAuthorFullName";

  @Mapping(target = "type", qualifiedByName = METHOD_MAP_REQUEST_CARD_TYPE)
  @Mapping(target = "lastPerformedAction", constant = CARD_ACTION_CREATED)
  @Mapping(target = "lastActionDateTime", expression = "java(LocalDateTime.now())")
  Card fromCreateRequest(CardCreateRequest request);

  @Mapping(target = "id", qualifiedByName = METHOD_MAP_RESPONSE_ID)
  @Mapping(target = "author.name", source = "author", qualifiedByName = METHOD_MAP_AUTHOR_FULL_NAME)
  @Mapping(target = "type", expression = "java(entity.getType().name())")
  CardResponse toResponse(Card entity);

  @Named(METHOD_MAP_REQUEST_CARD_TYPE)
  default CardType mapRequestCardType(String stringValue) {
    return stream(CardType.values())
        .filter(cardType -> cardType.name().equalsIgnoreCase(stringValue))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Unknown Card type."));
  }

  @Named(METHOD_MAP_RESPONSE_ID)
  default String mapResponseId(Long id) {
    assert id != null;

    var stringValue = valueOf(id);
    var valueLength = stringValue.length();

    if (valueLength >= CARD_ID_MIN_LENGHT) {
      return stringValue;
    }
    var difference = CARD_ID_MIN_LENGHT - valueLength;
    var stringBuilder = new StringBuilder(stringValue);

    for (byte i = 0; i < difference; i++) {
      stringBuilder.insert(0, 0);
    }
    return stringBuilder.toString();
  }

  @Named(METHOD_MAP_AUTHOR_FULL_NAME)
  default String mapAuthorFullName(User author) {
    assert author != null;
    // TODO: change to actual user name and surname;
    return format("%s %s", author.getUsername(), author.getUsername());
  }
}
