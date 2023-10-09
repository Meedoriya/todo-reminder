package com.alibi.todoreminderapi.api.controllers;

import com.alibi.todoreminderapi.api.dtos.TaskDto;
import com.alibi.todoreminderapi.api.exceptions.BadRequestException;
import com.alibi.todoreminderapi.api.factories.TaskDtoFactory;
import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskController {
         //TODO
    TaskRepository taskRepository;

    TaskDtoFactory taskDtoFactory;

    public static final String FETCH_TASK = "/api/tasks";
    public static final String CREATE_TASK = "/api/task";
    public static final String UPDATE_TASK = "/api/task";
    public static final String DELETE_TASK = "/api/tasks/{task_id}";

    @GetMapping(FETCH_TASK)
    public List<TaskDto> fetchTasks(
            @RequestParam(value = "title_prefix", required = false) Optional<String> optionalTitlePrefix) {

        optionalTitlePrefix = optionalTitlePrefix.filter(titlePrefix -> !titlePrefix.trim().isEmpty());

        Stream<TaskEntity> taskStream = optionalTitlePrefix
                .map(taskRepository::streamAllByTitleStartsWithIgnoreCase)
                .orElseGet(taskRepository::streamAllBy);

        if (optionalTitlePrefix.isPresent()) {
            taskStream = taskRepository.streamAllByTitleStartsWithIgnoreCase(optionalTitlePrefix.get());
        } else {
            taskStream = taskRepository.streamAllBy();
        }

        return taskStream
                .map(taskDtoFactory::makeTaskDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK)
    public TaskDto createTask(@RequestParam(value = "task_title") String taskTitle) {

        if (taskTitle.trim().isEmpty()) {
            throw new BadRequestException("Task title can't be empty");
        }

        boolean taskExists = taskRepository.findByTitle(taskTitle).isPresent();

        if (taskExists) {
            throw new BadRequestException(String.format("Project \"%s\" already exists.", taskTitle));
        }

        TaskEntity task = TaskEntity.builder().title(taskTitle).build();

        TaskEntity savedTask = taskRepository.saveAndFlush(task);

        return taskDtoFactory.makeTaskDto(savedTask);
    }

}
