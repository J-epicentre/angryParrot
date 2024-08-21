package com.project2.angry_parrot.repository;

import com.project2.angry_parrot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
