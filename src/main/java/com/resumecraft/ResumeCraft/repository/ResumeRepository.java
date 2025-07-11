package com.resumecraft.ResumeCraft.repository;
import com.resumecraft.ResumeCraft.model.Resume;
import com.resumecraft.ResumeCraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);

    Optional<Resume> findByIdAndUserId(Long id, Long id1);

    List<Resume> findByUserId(Long aLong);
}
