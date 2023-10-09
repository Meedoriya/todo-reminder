package com.alibi.todoreminderapi.api.dtos;

import com.alibi.todoreminderapi.store.entities.TaskEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class TaskStateDto {


    @NonNull
    Long id;
    @NonNull
    String name;

    @NonNull
    List<TaskDto> tasks;

}
