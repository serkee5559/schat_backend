package com.example.demo;

import com.example.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    // DB와 소통하기 위한 저장소(Repository) 주입
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Smart Star AI 금융 비서 서버 연결 성공!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        String userId = loginData.get("userId");
        String password = loginData.get("password");

        // 1. 게스트 계정 체크
        if ("guest".equals(userId) && "1234".equals(password)) {
            return "게스트"; //
        }

        // 2. 일반 DB 회원 조회 및 이름 반환
        try {
            return userRepository.findByUserId(userId)
                    .filter(user -> user.getPassword().equals(password))
                    .map(u -> u.getUserName()) // 👈 여기서 "success" 대신 실제 이름을 리턴!
                    .orElse("fail");
        } catch (Exception e) {
            System.err.println("DB 연결 오류: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, String> signupData) {
        try {
            String userId = signupData.get("userId");
            String password = signupData.get("password");
            String email = signupData.get("email");
            String userName = signupData.get("userName");

            // 1. 아이디 중복 확인 (DB 연결 시도)
            // [수정] DB 연결 에러 시 바로 catch로 가도록 try 안에 둡니다.
            if (userRepository.findByUserId(userId).isPresent()) {
                return "duplicate";
            }

            // 2. 새 사용자 저장
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setPassword(password);
            newUser.setUserName(email.split("@")[0]);
            newUser.setUserName(userName);

            userRepository.save(newUser);
            return "success";
        } catch (Exception e) {
            // 로그에 찍힌 것처럼 'Tenant or user not found' 등의 에러가 나면 여기로 옵니다.
            System.err.println("회원가입 오류: " + e.getMessage());
            return "error";
        }
    }
    // 히스토리 목록 불러오기
    // 1. 히스토리 목록 가져오기
    @GetMapping("/chat/history/{userId}")
    public ResponseEntity<List<ChatHistory>> getHistory(@PathVariable String userId) {
        return ResponseEntity.ok(chatHistoryRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    // 2. 채팅 시작 시 히스토리 저장
    @PostMapping("/chat/save")
    public ResponseEntity<ChatHistory> saveChat(@RequestBody Map<String, String> data) {
        ChatHistory history = new ChatHistory();
        history.setUserId(data.get("userId"));
        history.setTitle(data.get("title"));
        history.setLastMessage(data.get("lastMessage"));

        ChatHistory saved = chatHistoryRepository.save(history);
        return ResponseEntity.ok(saved);
    }

    // UserController.java 내부에 추가
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody Map<String, String> request) {
        String name = request.get("userName");
        String email = request.get("email");

        return userRepository.findByUserNameAndEmail(name, email)
                .map(user -> ResponseEntity.ok(user.getUserId()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("not_found"));
    }

    // 1. 특정 상담의 모든 메시지 불러오기
    @GetMapping("/chat/messages/{historyId}")
    public ResponseEntity<List<ChatMessage>> getMessages(@PathVariable UUID historyId) {
        return ResponseEntity.ok(chatMessageRepository.findByHistoryIdOrderByCreatedAtAsc(historyId));
    }

    // 2. 메시지 개별 저장 (채팅 발생 시마다 호출됨)
    @PostMapping("/chat/message/save")
    public ResponseEntity<ChatMessage> saveMessage(@RequestBody Map<String, String> data) {
        ChatMessage msg = new ChatMessage();
        msg.setHistoryId(UUID.fromString(data.get("historyId")));
        msg.setRole(data.get("role"));
        msg.setContent(data.get("content"));

        return ResponseEntity.ok(chatMessageRepository.save(msg));
    }
}