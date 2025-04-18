package com.project.text_share.Repo;

import com.project.text_share.Entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<MasterUser,Long> {
    Optional<MasterUser> findByUsername(String username);
    Optional<MasterUser> findByEmail(String email);
}
