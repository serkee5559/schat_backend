package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages", schema = "smart-star")
@Getter @Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // 어떤 상담(히스토리)에 속한 메시지인지 구분하는 ID
    private UUID historyId;

    // 'user' 또는 'assistant' (누가 보낸 메시지인지)
    private String role;

    // 메시지 내용 (말풍선 텍스트)
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}