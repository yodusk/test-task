package com.example.backend.repositories;

import com.example.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User findByUsername(String username);

    Optional<User> getByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    @Transactional
    void deleteById(Long id);
}
