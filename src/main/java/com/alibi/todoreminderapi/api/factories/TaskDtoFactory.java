package com.alibi.todoreminderapi.api.factories;

import com.alibi.todoreminderapi.api.dtos.TaskDto;
import com.alibi.todoreminderapi.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity taskEntity) {
        return TaskDto.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .dueDate(taskEntity.getDueDate())
                .reminderTime(taskEntity.getReminderTime())
                .build();
    }
}
