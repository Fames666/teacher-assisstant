//package by.ezubkova.teacher_assistant.library.auth.model.domain;
//
//import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;
//
//import by.ezubkova.teacher_assistant.library.auth.config.DatasourceConfiguration;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Inheritance;
//import jakarta.persistence.Table;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(schema = DatasourceConfiguration.DB_SCHEMA_AUTH)
//@Inheritance(strategy = TABLE_PER_CLASS)
//public class Authority implements GrantedAuthority {
//
//  @Id
//  private String name;
//
//  private String description;
//
//  @Override
//  public String getAuthority() {
//    return name;
//  }
//
//  public Authority(String name) {
//    this.name = name;
//  }
//}
