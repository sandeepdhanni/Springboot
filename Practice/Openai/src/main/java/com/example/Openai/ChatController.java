package com.example.Openai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest request) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", request.getMessages());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map response = restTemplate.postForObject(url, entity, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String reply = (String) message.get("content");
            return ResponseEntity.ok(Map.of("reply", reply));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "OpenAI API error: " + e.getMessage()));
        }
    }




    //THIS API WILL RETURN THE PATH FILES NAMES WHICH MATCH IN THE SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<String>> searchFiles(
            @RequestParam String searchText,
            @RequestParam String directoryPath) {

        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return ResponseEntity.badRequest().body(List.of("Invalid directory path"));
        }

        // Split the search text into words
        Set<String> searchWords = Arrays.stream(searchText.split("\\s+"))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        // Find matching files
        List<String> matchingFilePaths = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(File::isFile)
                .filter(file -> searchWords.stream().anyMatch(word -> file.getName().toLowerCase().contains(word)))
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        return ResponseEntity.ok(matchingFilePaths);
    }
}

