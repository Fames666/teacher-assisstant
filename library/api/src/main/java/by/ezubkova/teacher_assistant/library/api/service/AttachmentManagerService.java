package by.ezubkova.teacher_assistant.library.api.service;

import java.util.Map;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentManagerService {

  void saveNewAttachments(MultiValueMap<String, MultipartFile> newAttachments,
                          Map<String, String[]> parameters);
}
