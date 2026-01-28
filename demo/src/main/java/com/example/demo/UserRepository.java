package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 1. 로그인 시 아이디로 찾기
    Optional<User> findByUserId(String userId);

    // 2. 아이디 찾기: 이름과 이메일이 모두 일치하는 사용자 조회
    // 메서드 이름 규칙: findBy + 필드명(UserName) + And + 필드명(Email)
    Optional<User> findByUserNameAndEmail(String userName, String email);

    // 3. 비밀번호 재설정: 이름, 아이디, 이메일 삼중 확인
    Optional<User> findByUserNameAndUserIdAndEmail(String userName, String userId, String email);
}