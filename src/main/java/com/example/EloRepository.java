package com.example;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EloRepository extends JpaRepository<Elo, Long> {
    Elo findByUserId(Long userId);
}