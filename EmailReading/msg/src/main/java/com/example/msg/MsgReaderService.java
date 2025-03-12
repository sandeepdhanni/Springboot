package com.example.msg;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.MsgParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsgReaderService {

    public Map<String, Object> readMsgFile(MultipartFile file) throws Exception {
        MsgParser parser = new MsgParser();
        Map<String, Object> messageData = new HashMap<>();

        try (InputStream inputStream = file.getInputStream()) {
            Message msg = parser.parseMsg(inputStream);

            messageData.put("subject", msg.getSubject());
            messageData.put("from", msg.getFromEmail());
            messageData.put("to", msg.getDisplayTo());
            messageData.put("cc", msg.getDisplayCc());
            // Attempt to get body as RTF or HTML if plain text is unreadable
            String bodyText = msg.getBodyText();
            String bodyHtml = msg.getBodyHTML();

            messageData.put("body", msg.getBodyText());
        }

        return messageData;
    }
}
