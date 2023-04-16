package by.ezubkova.teacher_assistant.curriculum.jpa.model;

import static by.ezubkova.teacher_assistant.curriculum.api.constant.CardConstraints.CARD_TITLE_MAX_LENGTH;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

import by.ezubkova.teacher_assistant.curriculum.api.constant.CardType;
import by.ezubkova.teacher_assistant.user_management.jpa.model.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

  @Id
  @GeneratedValue(generator = "s_card_id", strategy = SEQUENCE)
  private Long id;

  @Column(nullable = false, length = CARD_TITLE_MAX_LENGTH)
  private String title;

  @ManyToOne(optional = false)
  @JoinColumn(name = "author_id",
              updatable = false,
              foreignKey = @ForeignKey(name = "fk_card_author"))
  private User author;

  private String lastPerformedAction;

  private LocalDateTime lastActionDateTime;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  //  private String location;

  @Enumerated(STRING)
  @Column(nullable = false)
  private CardType type;

  @OneToMany(fetch = EAGER)
  @JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "fk_cardprop_card"))
  private Set<CardProperty> properties;

  //  private List<String> members;

  //  private String description;

  //  private List<String> files;

  //  private List<String> links;

  //  private  commentsId;
}
