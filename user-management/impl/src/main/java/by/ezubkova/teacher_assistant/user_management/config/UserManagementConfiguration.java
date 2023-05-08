package by.ezubkova.teacher_assistant.user_management.config;

import static by.ezubkova.teacher_assistant.user_management.constant.UserManagementConstants.USER_MANAGEMENT_MODULE_MSG_SRC;

import by.ezubkova.teacher_assistant.common.factory.MessageSourceFactory;
import by.ezubkova.teacher_assistant.common.factory.product.PrefixedMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserManagementConfiguration {

  @Bean(USER_MANAGEMENT_MODULE_MSG_SRC)
  public PrefixedMessageSource userManagementMessageSource(MessageSourceFactory factory) {
    return factory.createMessageSourceForModule(USER_MANAGEMENT_MODULE_MSG_SRC);
  }
}
