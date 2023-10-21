package com.alibi.todoreminderapi.store.repositories;

import com.alibi.todoreminderapi.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

}
