package com.example.SpringAi_Tools_Declarative.controller;



import com.example.SpringAi_Tools_Declarative.tools.CustomTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

	private final ChatClient chatClient;
	private final CustomTools tools;

	@GetMapping("/chat")
	public String chat(@RequestParam String prompt) {
		return chatClient.prompt()
				.user(prompt)
				.call()
				.content();
	}

	@GetMapping("/chat-tools")
	public String chatWithTools(@RequestParam String prompt) {
		return chatClient.prompt()
				.tools(tools)
				.user(prompt)
				.call()
				.content();
	}
}