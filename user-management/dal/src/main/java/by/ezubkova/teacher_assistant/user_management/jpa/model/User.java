package by.ezubkova.teacher_assistant.user_management.jpa.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import by.ezubkova.teacher_assistant.user_management.jpa.model.extension.LastActivity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

  // TODO: merge in one table
  @OneToOne(mappedBy = "user", cascade = ALL, fetch = LAZY)
  private UserData userData;

  @Embedded
  private LastActivity lastActivity;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
