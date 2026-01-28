package com.example.demo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history", schema = "smart-star")
@Getter @Setter
public class ChatHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String userId;
    private String title;
    private String lastMessage;
    private LocalDateTime createdAt = LocalDateTime.now();
}