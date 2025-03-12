package com.example.msg;

import com.auxilii.msgparser.Message;
import com.auxilii.msgparser.MsgParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.datatypes.RecipientChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MsgReaderService {

//    public Map<String, Object> readMsgFile(MultipartFile file) throws Exception {
//        MsgParser parser = new MsgParser();
//        Map<String, Object> messageData = new HashMap<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            Message msg = parser.parseMsg(inputStream);
//
//            messageData.put("subject", msg.getSubject());
//            messageData.put("from", msg.getFromEmail());
//            messageData.put("to", msg.getDisplayTo());
//            messageData.put("cc", msg.getDisplayCc());
//            // Attempt to get body as RTF or HTML if plain text is unreadable
//            String bodyText = msg.getBodyText();
//            log.info("text body {}"+bodyText);
//            String bodyHtml = msg.getBodyHTML();
//            log.info("html body {}"+bodyHtml);
//
//            messageData.put("body", msg.getBodyText());
//        }
//
//        return messageData;
//    }



//    public Map<String, Object> readMsgFile(MultipartFile file) throws Exception {
//        MsgParser parser = new MsgParser();
//        Map<String, Object> messageData = new HashMap<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            Message msg = parser.parseMsg(inputStream);
//
//            // Debugging: Log the entire message object
//            log.info("Parsed Message: {}", msg);
//
//            String subject = msg.getSubject();
//            String fromEmail = msg.getFromEmail();
//            String fromName = msg.getFromName(); // Try getting the sender's name
//            String toRecipients = msg.getDisplayTo();
//            String ccRecipients = msg.getDisplayCc();
//
//            // Extract body using multiple methods
//            String bodyText = msg.getBodyText();
//            String bodyHtml = msg.getBodyHTML();
//            String rtfBody = msg.getBodyRTF();
//
//            // Logging to verify values
//            log.info("Subject: {}", subject);
//            log.info("From Email: {}", fromEmail);
//            log.info("From Name: {}", fromName);
//            log.info("To: {}", toRecipients);
//            log.info("CC: {}", ccRecipients);
//            log.info("Body Text: {}", bodyText);
//            log.info("Body HTML: {}", bodyHtml);
//            log.info("Body RTF: {}", rtfBody);
//
//            // Populate map with fallback checks
//            messageData.put("subject", subject != null ? subject : "No Subject");
//            messageData.put("from", fromEmail != null ? fromEmail : fromName);
//            messageData.put("to", toRecipients != null ? toRecipients : "No Recipient");
//            messageData.put("cc", ccRecipients != null ? ccRecipients : "No CC");
//
//            // Prefer HTML > Text > RTF for the body
//            String finalBody = bodyHtml != null ? bodyHtml : (bodyText != null ? bodyText : rtfBody);
//            messageData.put("body", finalBody != null ? finalBody : "No Body Content");
//
//        } catch (Exception e) {
//            log.error("Error reading .msg file: {}", e.getMessage(), e);
//            throw e;
//        }
//
//        return messageData;
//    }




    public Map<String, Object> readMsgFile(MultipartFile file) throws Exception {
        Map<String, Object> messageData = new HashMap<>();

        try (InputStream inputStream = file.getInputStream()) {
            MAPIMessage msg = new MAPIMessage(inputStream);

            // Extract basic fields
            String subject = safeGet(() -> msg.getSubject());
            String from = safeGet(() -> msg.getDisplayFrom());
            String to = safeGet(() -> msg.getDisplayTo());
            String cc = safeGet(() -> msg.getDisplayCC());

            // Try extracting the body (Text, then HTML, then RTF)
            String body = safeGet(() -> msg.getTextBody());
            if (body == null || body.isEmpty()) {
                body = safeGet(() -> msg.getHtmlBody());
            }
            if (body == null || body.isEmpty()) {
                body = safeGet(() -> msg.getRtfBody());
            }

            // Handle null or missing values with default text
            messageData.put("subject", subject != null ? subject : "No Subject");
            messageData.put("from", from != null ? from : extractFromRecipients(msg));
            messageData.put("to", to != null ? to : "No Recipient");
            messageData.put("cc", cc != null ? cc : "No CC");
            messageData.put("body", body != null ? body : "No Body Content");

            // Extract attachments safely
            List<String> attachments = new ArrayList<>();
            for (AttachmentChunks attachment : msg.getAttachmentFiles()) {
                String attachmentName = attachment.getAttachFileName() != null
                        ? attachment.getAttachFileName().toString()
                        : "Unknown Attachment";
                attachments.add(attachmentName);
            }
            messageData.put("attachments", attachments);

        } catch (Exception e) {
            log.error("General error parsing .msg file: {}", e.getMessage(), e);
            throw e;
        }

        return messageData;
    }

    // Helper method to safely get values and avoid ChunkNotFoundException
    private <T> T safeGet(SupplierWithException<T> supplier) {
        try {
            return supplier.get();
        } catch (ChunkNotFoundException e) {
            log.warn("Chunk not found: {}", e.getMessage());
            return null;
        }
    }

    // Attempt to extract sender details from recipients if not found via DisplayFrom
    private String extractFromRecipients(MAPIMessage msg) {
        try {
            for (RecipientChunks recipient : msg.getRecipientDetailsChunks()) {
                if (recipient.getRecipientEmailAddress() != null) {
                    return recipient.getRecipientEmailAddress().toString();
                }
            }
        } catch (Exception e) {
            log.warn("Could not extract sender from recipients: {}", e.getMessage());
        }
        return "Unknown Sender";
    }

    @FunctionalInterface
    private interface SupplierWithException<T> {
        T get() throws ChunkNotFoundException;
    }

}
