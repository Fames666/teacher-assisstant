package by.ezubkova.teacher_assistant.library.constant;

import static by.ezubkova.teacher_assistant.library.constant.LibraryConstraints.EXTENSION_SEPARATOR_REGEX;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Locale.ROOT;

public enum Extension {

  PDF(".pdf"), DOCX(".docx"), XLSX(".xlsx"),
  ZIP(".zip"), RAR(".rar"),
  JPG(".jpg"), JPEG(".jpeg"), PNG(".png"),
  UNKNOWN("");

  private final String value;

  Extension(String value) {
    this.value = value;
  }

  public static Extension resolveFromFilename(String filename) {
    var nameChunks = filename.toLowerCase(ROOT).split(EXTENSION_SEPARATOR_REGEX);
    var nonPrefixedExtension = nameChunks[nameChunks.length - 1];
    var prefixedExtension = format(".%s", nonPrefixedExtension);
    return findByValue(prefixedExtension);
  }

  public static Extension findByValue(String value) {
    return stream(values())
        .filter(extension -> !UNKNOWN.equals(extension))
        .filter(extension -> extension.toString().equals(value))
        .findFirst().orElse(UNKNOWN);
  }

  public String toString() {
    return value;
  }
}
