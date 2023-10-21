package com.alibi.todoreminderapi.api.service;

import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskReminderService {

    TaskRepository taskRepository;

    NotificationService notificationService;

    @Scheduled(cron = "0 */30 * * * *")  // Например, каждые 30 минут
    public void checkTasksForReminders() {
        List<TaskEntity> tasksToRemind = taskRepository.findTasksForReminder();

        tasksToRemind.forEach(task -> {
            String firebaseToken = task.getUser().getFirebaseToken();
            notificationService.sendNotification(task.getFirebaseToken(), "Task reminder", "You have a task: "
                    + task.getTitle() + " from Task State: " + task.getTaskStateEntity().getName());
        });
    }
}
