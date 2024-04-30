package com.example;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBetRepository extends JpaRepository<UserBet, Long> {
	
	@Query("SELECT ub FROM UserBet ub WHERE ub.userId <> ?1 AND ub.venue = ?2 AND ub.timings = ?3")
    List<UserBet> findTop5ByUserIdNotAndVenueAndTimingsOrderByBetDesc(Long userId, String venue, String timings);
	
	
}
