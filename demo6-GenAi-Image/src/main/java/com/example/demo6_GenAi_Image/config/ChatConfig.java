package com.example.demo6_GenAi_Image.config;

import com.example.demo6_GenAi_Image.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import lombok.extern.slf4j.Slf4j;

/**
 * @author abhishekvermaa10
 */
@Slf4j
@Configuration
public class ChatConfig {

	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor) {
		return chatClientBuilder
				.defaultAdvisors(loggingAdvisor)
				.build();
	}

}
