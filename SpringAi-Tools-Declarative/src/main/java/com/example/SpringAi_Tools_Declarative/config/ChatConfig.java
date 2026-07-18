package com.example.SpringAi_Tools_Declarative.config;


import com.example.SpringAi_Tools_Declarative.advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder,
	                             LoggingAdvisor loggingAdvisor) {
		return builder
				.defaultAdvisors(loggingAdvisor)
				.build();
	}
}