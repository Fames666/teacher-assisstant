package by.ezubkova.teacher_assistant.library.service;

import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileSystemOperatorService {

  @Value("${app-config.attachments.storage.base-path}")
  private final String attachmentsBaseStoragePath;

  @PostConstruct
  private void initFilesBaseDir() {
    var attachmentsBaseDir = new File(attachmentsBaseStoragePath);
    if (attachmentsBaseDir.exists()) {
      return;
    }
    if (!attachmentsBaseDir.mkdirs()) {
      throw new IllegalStateException("Failed to initialize base directory for storable files");
    }
  }

  public String buildPath(Long attachmentId,
                          String attachmentName,
                          String... directoriesHierarchy) {
    byte[] bytes = format("%d%s", attachmentId, attachmentName).getBytes();
    var generatedFilename = md5DigestAsHex(bytes);

    var directoryPath = get(attachmentsBaseStoragePath, directoriesHierarchy).toString();
    return get(directoryPath, generatedFilename).toString();
  }

  public void saveFile(String absolutePath, MultipartFile file) {
    try {
      var localFile = new File(absolutePath);
      if (!localFile.exists() && !localFile.createNewFile()) {
        throw new IOException(format("Cannot create file: %s", absolutePath));
      }
      file.transferTo(localFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
