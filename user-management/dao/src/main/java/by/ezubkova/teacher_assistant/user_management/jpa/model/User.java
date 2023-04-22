package by.ezubkova.teacher_assistant.user_management.jpa.model;

import static jakarta.persistence.CascadeType.ALL;
import static org.hibernate.annotations.FetchMode.JOIN;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

  @Id
  //  @GeneratedValue(strategy = UUID)
  private String id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  //  @ManyToMany
  //  @Fetch(JOIN)
  //  @JoinTable(name = "user_role",
  //             schema = DatasourceConfiguration.DB_SCHEMA_AUTH,
  //             joinColumns = @JoinColumn(name = "user_id",
  //                                       foreignKey = @ForeignKey(name = "fk_userrole_user")),
  //             inverseJoinColumns = @JoinColumn(name = "role_id",
  //                                              foreignKey = @ForeignKey(name = "fk_userrole_role")))
  //  private Set<Role> roles;

  private boolean accountNonExpired;

  @Column(nullable = false)
  private boolean accountNonLocked;

  private boolean credentialsNonExpired;

  @Column(nullable = false)
  private boolean enabled;

  @OneToOne(mappedBy = "user", cascade = ALL)
  @Fetch(JOIN)
  private UserData userData;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
