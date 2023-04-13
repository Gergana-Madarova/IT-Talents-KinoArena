package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     boolean existsByEmail(String email);
}
