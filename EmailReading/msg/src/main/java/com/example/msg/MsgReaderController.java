package com.example.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/msg")
public class MsgReaderController {

    @Autowired
    private MsgReaderService msgReaderService;

    @PostMapping("/read")
    public ResponseEntity<Map<String, Object>> readMsgFile(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, Object> messageData = msgReaderService.readMsgFile(file);
            return ResponseEntity.ok(messageData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
