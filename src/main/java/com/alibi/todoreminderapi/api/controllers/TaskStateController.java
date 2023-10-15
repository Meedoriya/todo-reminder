package com.alibi.todoreminderapi.api.controllers;


import com.alibi.todoreminderapi.api.dtos.TaskStateDto;
import com.alibi.todoreminderapi.api.exceptions.BadRequestException;
import com.alibi.todoreminderapi.api.factories.TaskStateDtoFactory;
import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import com.alibi.todoreminderapi.store.repositories.TaskStateRepository;
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
public class TaskStateController {

    // TODO

    TaskStateRepository taskStateRepository;

    TaskStateDtoFactory taskStateDtoFactory;

    public static final String FETCH_TASK = "/api/task-states";
    public static final String CREATE_TASK = "/api/task-state";
    public static final String UPDATE_TASK = "/api/task-state";
    public static final String DELETE_TASK = "/api/task-states/{task_state_id}";

    @GetMapping(FETCH_TASK)
    public List<TaskStateDto> fetchTasks(
            @RequestParam(value = "title_prefix", required = false) Optional<String> optionalTitlePrefix) {

        optionalTitlePrefix = optionalTitlePrefix.filter(titlePrefix -> !titlePrefix.trim().isEmpty());

        Stream<TaskStateEntity> taskStateStream = optionalTitlePrefix
                .map(taskStateRepository::streamAllByTitleStartsWithIgnoreCase)
                .orElseGet(taskStateRepository::streamAllBy);

        if (optionalTitlePrefix.isPresent()) {
            taskStateStream = taskStateRepository.streamAllByTitleStartsWithIgnoreCase(optionalTitlePrefix.get());
        } else {
            taskStateStream = taskStateRepository.streamAllBy();
        }

        return taskStateStream
                .map(taskStateDtoFactory::makeTaskStateDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_TASK)
    public TaskStateDto createTaskState(@RequestParam(name = "task_state_name") String taskStateName) {

        if (taskStateName.trim().isEmpty()) {
            throw new BadRequestException("Task state name can't be empty.");
        }

        Optional<TaskStateEntity> optionalTaskState = taskStateRepository.findByName(taskStateName);

        if (optionalTaskState.isPresent()) {
            throw new BadRequestException(String.format("Task state \"%s\" already exists.", taskStateName));
        }

        TaskStateEntity taskState = taskStateRepository.saveAndFlush(TaskStateEntity
                .builder()
                .name(taskStateName)
                .build());

        final TaskStateEntity savedTaskState = taskStateRepository.saveAndFlush(taskState);

        return taskStateDtoFactory.makeTaskStateDto(savedTaskState);
    }

}
