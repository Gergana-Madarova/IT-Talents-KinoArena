package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     boolean existsByEmail(String email);
     Optional<User>findByEmail(String email);
     Optional<User>findAllByConfirmatronToken(String token);


     List<User> findAllByEnableFalse();


}
