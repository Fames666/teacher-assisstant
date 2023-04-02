package by.ezubkova.teacher_assistant.library.model;

import static by.ezubkova.teacher_assistant.library.constant.LibraryConstraints.LIBRARY_DB_SCHEMA;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

import by.ezubkova.teacher_assistant.library.constant.Extension;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = LIBRARY_DB_SCHEMA)
@NoArgsConstructor
public class AttachmentEntry {

  @Id
  @GeneratedValue(strategy = SEQUENCE, generator = "s_attachment_entry")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private String name;

  @Column(nullable = false, updatable = false)
  @Enumerated(STRING)
  private Extension extension;

  private String description;

  private String localStoragePath;

  @Transient
  private MultipartFile file;

  public AttachmentEntry(String name,
                         Extension extension,
                         String description,
                         MultipartFile multipartFile) {
    this.name = name;
    this.extension = extension;
    this.description = description;
    this.file = multipartFile;
  }
}
