package by.ezubkova.teacher_assistant.curriculum.jpa.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

import by.ezubkova.teacher_assistant.curriculum.api.constant.CardPropertyType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class CardProperty {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "s_card_property")
  private Long id;

  @Column(nullable = false, updatable = false)
  private String name;

  @Column(nullable = false, updatable = false)
  @Enumerated(STRING)
  private CardPropertyType valueType;

  @EqualsAndHashCode.Exclude
  @Column(nullable = false)
  private byte[] value;
}
