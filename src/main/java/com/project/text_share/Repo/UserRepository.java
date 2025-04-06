package com.project.text_share.Repo;

import com.project.text_share.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
