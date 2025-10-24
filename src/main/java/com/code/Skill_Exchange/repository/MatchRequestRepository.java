package com.code.Skill_Exchange.repository;

import com.code.Skill_Exchange.model.MatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRequestRepository extends JpaRepository<MatchRequest, Long> {
    List<MatchRequest> findByReceiverId(Long receiverId);
    List<MatchRequest> findBySenderId(Long senderId);
}
