package com.example.SpringAi_Tools_Declarative.advisor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingAdvisor implements CallAdvisor {

	@Override
	public String getName() {
		return "LoggingAdvisor";
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
		log.info("Request: {}", request);
		ChatClientResponse response = chain.nextCall(request);
		log.info("Response: {}", response);
		return response;
	}
}