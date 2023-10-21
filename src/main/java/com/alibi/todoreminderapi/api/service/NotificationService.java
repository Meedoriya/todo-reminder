package com.alibi.todoreminderapi.api.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class NotificationService {

    UserService userService;

    public void sendNotification(String firebaseToken, String title, String message) {
        Message msg = Message.builder()
                .putData("title", title)
                .putData("message", message)
                .setToken(firebaseToken)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(msg);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

