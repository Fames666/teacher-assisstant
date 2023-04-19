//package by.ezubkova.teacher_assistant.library.auth.model.domain;
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
//public class Group extends Authority {
//
//  @ManyToMany
//  @Fetch(JOIN)
//  @JoinTable(name = "group_role",
//             schema = DatasourceConfiguration.DB_SCHEMA_AUTH,
//             joinColumns = @JoinColumn(name = "group_id",
//                                       foreignKey = @ForeignKey(name = "fk_grouprole_group")),
//             inverseJoinColumns = @JoinColumn(name = "role_id",
//                                              foreignKey = @ForeignKey(name = "fk_grouprole_role")))
//  private Set<Role> roles;
//
//  @ManyToMany
//  @Fetch(JOIN)
//  @JoinTable(name = "group_authority",
//             schema = DatasourceConfiguration.DB_SCHEMA_AUTH,
//             joinColumns = @JoinColumn(name = "group_id",
//                                       foreignKey = @ForeignKey(name = "fk_grouprole_group")),
//             inverseJoinColumns = @JoinColumn(name = "authority_id",
//                                              foreignKey = @ForeignKey(name = "fk_grouprole_authority")))
//  private Set<Authority> authorities;
//}
