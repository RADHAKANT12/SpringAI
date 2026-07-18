package com.example.SpringAi_Tools_Declarative.tools;



import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomTools {

	@Tool(name = "getCurrentDateTime",
			description = "Get current date and time")
	public String getCurrentDateTime() {
		return LocalDateTime.now().toString();
	}

	@Tool(name = "print",
			description = "Print message to console")
	public String print(String message) {
		System.out.println("Printed: " + message);
		return "Printed successfully";
	}
}