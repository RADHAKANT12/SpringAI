package com.example.demo3_advisors.config;

import com.example.demo3_advisors.advisors.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor,
                          SimpleLoggerAdvisor simpleLoggerAdvisor) {
        return chatClientBuilder
                .defaultAdvisors(loggingAdvisor)
                .defaultAdvisors(simpleLoggerAdvisor)
                .build();
    }

    @Bean
    SimpleLoggerAdvisor simpleLoggerAdvisor() {
        return new SimpleLoggerAdvisor();
    }

}