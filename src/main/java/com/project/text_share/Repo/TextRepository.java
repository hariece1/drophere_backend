package com.project.text_share.Repo;

import com.project.text_share.Entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextRepository extends JpaRepository<Text,Long> {
    Optional<Text> findBySlug(String slug);
}
