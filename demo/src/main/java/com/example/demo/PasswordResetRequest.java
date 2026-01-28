// PasswordResetRequest.java
package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordResetRequest {
    private String userName; // 이름
    private String userId;   // 아이디
    private String email;    // 이메일
    private String newPassword; // 새로 바꿀 비밀번호
}