package com.example.demo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// User.java (엔티티 클래스)
@Entity
@Table(name = "users", schema = "smart-star")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userName;
    private String password;
    private String email;
}