package com.alibi.todoreminderapi.store.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="task_state")
public class TaskStateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    Long id;
    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "taskStateEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskEntity> tasks = new ArrayList<>();

}
