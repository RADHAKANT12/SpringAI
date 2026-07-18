package com.example.Spring_Ai2.controller;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/ai")
public class AdvancedChatController {

    private static final Logger log = LoggerFactory.getLogger(AdvancedChatController.class);

    private final ChatClient chatClient;
    private final Resource tweetSystemMsgResource;

    public AdvancedChatController(ChatClient.Builder builder,
                                  @Value("classpath:/prompts/tweet-system-message.st")
                                  Resource tweetSystemMsgResource) {

        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        this.tweetSystemMsgResource = tweetSystemMsgResource;
    }

    // ==========================
    // ✅ 1. Funny Chat
    // ==========================
    @PostMapping("/chat")
    public Output chat(@RequestBody @Valid Input input) {

        Prompt prompt = new Prompt(List.of(
                new SystemMessage("You are funny and sarcastic."),
                new UserMessage(input.prompt())
        ));

        String response = chatClient.prompt(prompt)
                .call()
                .content();

        return new Output(response);
    }

    // ==========================
    // ✅ 2. Title Suggestions
    // ==========================
    @PostMapping("/suggest-titles")
    public TitleSuggestionsResponse suggestTitles(@RequestBody @Valid TitleSuggestionsRequest req) {

        ListOutputConverter converter = new ListOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
                Topic:
                {topic}

                Generate {count} short titles.

                {format}
                """);

        Message message = pt.createMessage(Map.of(
                "topic", req.topic(),
                "count", req.count(),
                "format", converter.getFormat()
        ));

        String response = chatClient.prompt()
                .messages(message)
                .call()
                .content();

        return new TitleSuggestionsResponse(converter.convert(response));
    }

    // ==========================
    // ✅ 3. Languages API
    // ==========================
    @GetMapping("/languages")
    public Map<String, Object> languages() {

        MapOutputConverter converter = new MapOutputConverter();

        PromptTemplate pt = new PromptTemplate("""
                Return programming languages with year.

                {format}
                """);

        Message message = pt.createMessage(Map.of("format", converter.getFormat()));

        String response = chatClient.prompt()
                .messages(message)
                .call()
                .content();

        return converter.convert(response);
    }

    // ==========================
    // ✅ 4. Tweet Generator
    // ==========================
    @PostMapping("/gen-tweet")
    public Tweet generateTweet(@RequestBody @Valid Input input) throws IOException {

        String systemPrompt = tweetSystemMsgResource.getContentAsString(UTF_8);

        BeanOutputConverter<Tweet> converter = new BeanOutputConverter<>(Tweet.class);

        PromptTemplate pt = new PromptTemplate("""
                Create tweet:

                {content}

                {format}
                """);

        Message userMessage = pt.createMessage(Map.of(
                "content", input.prompt(),
                "format", converter.getFormat()
        ));

        Prompt prompt = new Prompt(List.of(
                new SystemMessage(systemPrompt),
                userMessage
        ));

        String response = chatClient.prompt(prompt)
                .call()
                .content();

        return converter.convert(response);
    }

    // ==========================
    // ✅ 5. Support Bot
    // ==========================
    @GetMapping("/support")
    public ResponseEntity<String> support(@RequestParam String userPrompt) {

        String systemPrompt = """
                You are support assistant.
                Only answer Java & Spring Boot.
                """;

        String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

        return ResponseEntity.ok(response);
    }

    // ==========================
    // DTOs
    // ==========================
    public record Input(@NotBlank String prompt) {}

    public record Output(String content) {}

    public record TitleSuggestionsRequest(
            @NotBlank String topic,
            @NotNull Integer count
    ) {}

    public record TitleSuggestionsResponse(List<String> titles) {}

    public record Tweet(String content, List<String> hashtags) {}
}