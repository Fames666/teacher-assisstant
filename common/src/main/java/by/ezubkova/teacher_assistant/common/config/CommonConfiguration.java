package by.ezubkova.teacher_assistant.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessageSourceConfigurationProperties.class)
public class CommonConfiguration {
}
