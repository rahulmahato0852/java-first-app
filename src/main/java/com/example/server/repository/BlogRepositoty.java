package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.model.Blog;

public interface BlogRepositoty extends JpaRepository<Blog, Long> {
    // Custom finder method
    // Optional<Blog> findByEmail(String email);
}
