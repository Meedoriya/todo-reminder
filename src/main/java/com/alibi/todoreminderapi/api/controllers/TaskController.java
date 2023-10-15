package com.alibi.todoreminderapi.api.controllers;

import com.alibi.todoreminderapi.api.controllers.helper.ControllerHelper;
import com.alibi.todoreminderapi.api.dtos.AckDto;
import com.alibi.todoreminderapi.api.dtos.TaskDto;
import com.alibi.todoreminderapi.api.exceptions.BadRequestException;
import com.alibi.todoreminderapi.api.factories.TaskDtoFactory;
import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import com.alibi.todoreminderapi.store.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskController {

    TaskRepository taskRepository;

    ControllerHelper controllerHelper;

    TaskDtoFactory taskDtoFactory;

    public static final String GET_TASKS = "/api/tasks";
    public static final String CREATE_TASK = "/api/task";
    public static final String UPDATE_TASK = "/api/task";
    public static final String DELETE_TASK = "/api/tasks/{task_id}";

    @GetMapping(GET_TASKS)
    public List<TaskDto> getTasks(
            @PathVariable(value = "task_state_id") Long taskStateId) {

        TaskStateEntity taskState = controllerHelper.getTaskStateOrThrowException(taskStateId);

        return taskState
                .getTasks()
                .stream()
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
    
    @PutMapping(UPDATE_TASK)
    public TaskDto updateTask(
            @RequestParam(value = "task_id") Long taskId,
            @RequestParam(value = "task_name", required = false) Optional<String> optionalTaskName) {

        optionalTaskName = optionalTaskName.filter(name -> !name.trim().isEmpty());

        if (optionalTaskName.isEmpty()) {
            throw new BadRequestException("Task name can't be empty");
        }

        TaskEntity task = controllerHelper.getTaskOrThrowException(taskId);

        optionalTaskName.ifPresent(taskName -> {
            taskRepository
                    .findByTitle(taskName)
                    .filter(anotherTask -> !Objects.equals(anotherTask.getId(), task.getId()))
                    .ifPresent(anotherTask -> {
                        throw new BadRequestException(
                                String.format("Task \"%s\" task already exists.", taskName)
                        );
                    });

            task.setTitle(taskName);
        });

        TaskEntity savedTask = taskRepository.saveAndFlush(task);

        return taskDtoFactory.makeTaskDto(savedTask);
    }

    @DeleteMapping(DELETE_TASK)
    public AckDto deleteTask(@PathVariable(value = "task_id") Long taskId) {

        controllerHelper.getTaskOrThrowException(taskId);

        taskRepository.deleteById(taskId);

        return AckDto.makeDefault(true);
    }
}
