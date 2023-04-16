package by.ezubkova.teacher_assistant.user_management.controller;

import java.net.http.HttpResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/login", method = RequestMethod.POST)
public class AuthenticationController {

  public HttpResponse<String> logIn(@RequestBody Map<String, Object> formData) {
    return null;
  }
}
