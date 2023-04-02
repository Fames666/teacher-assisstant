package by.ezubkova.teacher_assistant.library.service;

import static by.ezubkova.teacher_assistant.library.constant.Extension.resolveFromFilename;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.UUID.randomUUID;

import by.ezubkova.teacher_assistant.library.api.service.AttachmentManagerService;
import by.ezubkova.teacher_assistant.library.model.AttachmentEntry;
import by.ezubkova.teacher_assistant.library.repository.AttachmentRepository;
import by.ezubkova.teacher_assistant.library.util.CommonUtil.CollectionUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAttachmentManagerService implements AttachmentManagerService {

  private final AttachmentRepository repository;
  private final FileSystemOperatorService fileSystemOperatorService;

  @Override
  public void saveNewAttachments(MultiValueMap<String, MultipartFile> newAttachments,
                                 Map<String, String[]> parameters) {
    var attachmentEntries = newAttachments.entrySet().stream()
        .map(newAttachmentRequest -> createAttachmentEntry(newAttachmentRequest, parameters))
        .filter(Objects::nonNull)
        .toList();
    attachmentEntries = repository.saveAll(attachmentEntries);
    attachmentEntries.forEach(this::setAttachmentStoragePath);
    attachmentEntries.forEach(entry -> fileSystemOperatorService.saveFile(entry.getLocalStoragePath(),
                                                                          entry.getFile()));
  }

  private AttachmentEntry createAttachmentEntry(Entry<String, List<MultipartFile>> newAttachmentRequest,
                                                Map<String, String[]> parameters) {
    var requestName = newAttachmentRequest.getKey();
    var description = ofNullable(parameters.get(requestName))
        .filter(params -> params.length != 0)
        .map(params -> params[0])
        .orElse(null);
    var requestAttachment = of(newAttachmentRequest.getValue())
        .filter(CollectionUtil::nonEmpty)
        .map(attachments -> attachments.iterator().next())
        .orElse(null);
    return requestAttachment != null ?
        createAttachmentEntry(requestAttachment, description) : null;
  }

  private AttachmentEntry createAttachmentEntry(MultipartFile multipartFile, String description) {
    var fullFileName = ofNullable(multipartFile.getOriginalFilename())
        .orElse(randomUUID().toString());
    var extension = resolveFromFilename(fullFileName);
    var fileName = fullFileName.substring(0, fullFileName.lastIndexOf(extension.toString()));
    return new AttachmentEntry(fileName, extension, description, multipartFile);
  }

  private void setAttachmentStoragePath(AttachmentEntry entry) {
    var path = fileSystemOperatorService.buildPath(entry.getId(), entry.getName());
    entry.setLocalStoragePath(path);
  }
}
