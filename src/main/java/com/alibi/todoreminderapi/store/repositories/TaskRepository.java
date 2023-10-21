package com.alibi.todoreminderapi.store.repositories;

import com.alibi.todoreminderapi.store.entities.TaskEntity;
import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findTasksForReminder();

    Optional<TaskEntity> findByTitle(String title);

    Stream<TaskEntity> streamAllBy();

    Stream<TaskEntity> streamAllByTitleStartsWithIgnoreCase(String titlePrefix);
}
