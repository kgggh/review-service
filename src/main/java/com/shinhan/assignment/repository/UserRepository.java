package com.shinhan.assignment.repository;

import com.shinhan.assignment.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
