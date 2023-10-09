package com.alibi.todoreminderapi.store.repositories;

import com.alibi.todoreminderapi.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByTitle(String title);

    Stream<TaskEntity> streamAllBy();

    Stream<TaskEntity> streamAllByTitleStartsWithIgnoreCase(String titlePrefix);
}
