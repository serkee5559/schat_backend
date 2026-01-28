package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    // 특정 히스토리 ID를 가진 메시지들을 생성 시간 순서대로 모두 가져오기
    List<ChatMessage> findByHistoryIdOrderByCreatedAtAsc(UUID historyId);
}