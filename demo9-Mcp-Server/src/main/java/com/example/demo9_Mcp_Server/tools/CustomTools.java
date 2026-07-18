package com.example.demo9_Mcp_Server.tools;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author abhishekvermaa10
 */
@Component
public class CustomTools {
	
	//@Tool(name = "getCurrentDateTime", description = "Get the current date and time in the user's timezone.")
	@Tool(
			name = "getCurrentDateTime",
			description = "Use this tool when user asks for current date or time. Always use this tool instead of guessing."
	)
	public String getCurrentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}


	@Tool(name = "print", description = "Print the given message to the console.")
	public void print(String message) {
		System.out.println("Printed: " + message);		
	}

}
