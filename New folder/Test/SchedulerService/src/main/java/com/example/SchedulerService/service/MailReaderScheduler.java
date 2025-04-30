package com.example.SchedulerService.service;

import com.example.SchedulerService.config.MailProperties;
import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailReaderScheduler {

    private final MailProperties mailProperties;
    private final MailReaderService mailReaderService;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

//    @Scheduled(cron = "*/5 * * * * *")
//    @Scheduled(cron = "0 0 9 * * MON-SAT")
    public void readTodayUnreadMails() {
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            return;
        }

        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");

        try (Store store = Session.getInstance(props).getStore()) {
            store.connect(mailProperties.getHost(), mailProperties.getUsername(), mailProperties.getPassword());

            try (Folder inbox = store.getFolder("INBOX")) {
                inbox.open(Folder.READ_ONLY);
                Message[] unreadMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                log.info("Unread messages count: {}", unreadMessages.length);
                log.info("unread messages : {}", (Object) unreadMessages);

                String today = new SimpleDateFormat(DATE_FORMAT).format(new Date());
                log.info("today's date is : {}", today);

                for (Message message : unreadMessages) {
                    if (isTodayAndMatchSubject(message)) {
                        log.info("✅ Matching Message Found:");
                        log.info("Subject: {}", message.getSubject());
                        log.info("Received At: {}", message.getReceivedDate());
                        mailReaderService.saveEmail(message);
                    }
                }
            }

        } catch (Exception e) {
            log.info("Mail reading failed: {}", e.getMessage());
        }
    }


private boolean isTodayAndMatchSubject(Message message) {
    try {
        String subject = message.getSubject();
        Date receivedDate = message.getReceivedDate();

        if (subject == null || receivedDate == null) {
            return false;
        }

        LocalDate messageDate = receivedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        log.info("subject is : {}", mailProperties.getSubjects());

        boolean subjectMatches = mailProperties.getSubjects().stream()
                .anyMatch(s -> subject.toLowerCase().contains(s.toLowerCase()));
        boolean dateMatches = messageDate.equals(today);

        return subjectMatches && dateMatches;
    } catch (MessagingException e) {
        log.error("Error checking message metadata: {}", e.getMessage(), e);
        return false;
    }
}
}

