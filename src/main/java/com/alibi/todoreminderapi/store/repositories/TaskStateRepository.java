package com.alibi.todoreminderapi.store.repositories;

import com.alibi.todoreminderapi.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity, Long> {
}
