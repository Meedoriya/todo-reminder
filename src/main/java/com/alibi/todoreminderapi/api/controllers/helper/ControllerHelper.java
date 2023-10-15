package com.alibi.todoreminderapi.api.controllers.helper;

import com.alibi.todoreminderapi.api.exceptions.NotFoundException;
import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import com.alibi.todoreminderapi.store.repositories.TaskRepository;
import com.alibi.todoreminderapi.store.repositories.TaskStateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Component
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ControllerHelper {

    TaskRepository taskRepository;

    TaskStateRepository taskStateRepository;

    public TaskStateEntity getTaskStateOrThrowException(@PathVariable("task_state_id") Long taskStateId) {
        return taskStateRepository
                .findById(taskStateId)
                .orElseThrow(() -> new NotFoundException("We didn't find the task state with id " + taskStateId));
    }

    public TaskEntity getTaskOrThrowException(@PathVariable("task_id") Long taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new NotFoundException("We didn't find the task with id " + taskId));
    }
}
