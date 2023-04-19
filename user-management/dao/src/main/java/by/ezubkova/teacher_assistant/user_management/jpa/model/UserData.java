package by.ezubkova.teacher_assistant.user_management.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserData {

  @Id
  @MapsId
  @OneToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_userdata_user"))
  private User user;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String secondName;

  private String thirdName;

  public UserData(String firstName, String secondName, String thirdName) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
  }
}
