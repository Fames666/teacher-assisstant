package by.ezubkova.teacher_assistant.user_management.jpa.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserData {

  @Id
  private String id;

  @OneToOne(optional = false, fetch = LAZY)
  @PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "fk_userdata_user"))
  private User user;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String secondName;

  private String thirdName;
}
