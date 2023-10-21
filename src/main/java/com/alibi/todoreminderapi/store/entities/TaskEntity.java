package com.alibi.todoreminderapi.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="task")
public class TaskEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    @Column(name = "title")
    String title;
    @Column(name = "description")
    String description;

    LocalDateTime dueDate;

    @ManyToOne
    User user;

    LocalDateTime reminderTime;

    String firebaseToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_state_id")
    TaskStateEntity taskStateEntity;
}
