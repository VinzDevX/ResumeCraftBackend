package com.resumecraft.ResumeCraft.repository;

import com.resumecraft.ResumeCraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

 public  User findByEmail(String email);

 Optional<User> findOptionalByEmail(String email);
}
