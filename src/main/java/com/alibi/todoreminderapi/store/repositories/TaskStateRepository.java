package com.alibi.todoreminderapi.store.repositories;

import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {

    Optional<TaskStateEntity> findByName(String taskStateName);
    Stream<TaskStateEntity> streamAllBy();

    Stream<TaskStateEntity> streamAllByTitleStartsWithIgnoreCase(String titlePrefix);

    Optional<TaskStateEntity> findTaskStateEntityByIdAndNameContainsIgnoreCase(Long projectId, String taskStateName);


}
