package com.alibi.todoreminderapi.api.controllers;


import com.alibi.todoreminderapi.api.exceptions.NotFoundException;
import com.alibi.todoreminderapi.store.entities.User;
import com.alibi.todoreminderapi.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class UserController {

    UserRepository userRepository;

    @PutMapping("/{userId}/firebase-token")
    public ResponseEntity<Void> updateFirebaseToken(@PathVariable Long userId, @RequestBody String firebaseToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        user.setFirebaseToken(firebaseToken);
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
