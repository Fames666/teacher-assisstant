//package by.ezubkova.teacher_assistant.user_management.model.jpa;
//
//import static org.hibernate.annotations.FetchMode.JOIN;
//
//import by.ezubkova.teacher_assistant.library.auth.config.DatasourceConfiguration;
//import jakarta.persistence.*;
//import java.util.Set;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Fetch;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(schema = DatasourceConfiguration.DB_SCHEMA_AUTH)
//public class Role extends Authority {
//
//  @ManyToMany
//  @Fetch(JOIN)
//  @JoinTable(name = "role_authority",
//             schema = DatasourceConfiguration.DB_SCHEMA_AUTH,
//             joinColumns = @JoinColumn(name = "role_id",
//                                       foreignKey = @ForeignKey(name = "fk_roleauthority_role")),
//             inverseJoinColumns = @JoinColumn(name = "authority_id",
//                                              foreignKey = @ForeignKey(name = "fk_roleauthority_authority")))
//  private Set<Authority> authorities;
//
//  public Role(String name) {
//    super(name);
//  }
//}
