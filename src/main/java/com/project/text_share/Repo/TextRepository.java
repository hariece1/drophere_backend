package com.project.text_share.Repo;

import com.project.text_share.Entity.Text;
import com.project.text_share.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TextRepository extends JpaRepository<Text,Long> {
    Optional<Text> findBySlug(String slug);
    List<Text> findByUser(User user);

}
