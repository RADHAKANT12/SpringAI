package com.example.demo9_Mcp_Server.config;

import com.example.demo9_Mcp_Server.tools.CustomTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @author abhishekvermaa10
 */
@Configuration
public class McpServerConfig {
	
	@Bean
	ToolCallbackProvider toolCallbackProvider(CustomTools customTools) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(customTools)
				.build();
	}

}
