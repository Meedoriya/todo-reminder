package com.alibi.todoreminderapi.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TaskDto {

    Long id;
    String title;
    String description;
    @JsonProperty("due_date")
    LocalDateTime dueDate;
    @JsonProperty("reminder_time")
    LocalDateTime reminderTime;

}
