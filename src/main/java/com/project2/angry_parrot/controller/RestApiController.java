package com.project2.angry_parrot.controller;

import com.project2.angry_parrot.model.User;
import com.project2.angry_parrot.repository.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String home() {
        return "<h1>home</h1>";
    }

    @GetMapping("/userInfo")
    public String userInfo() {
        return "userInfo";
    }

    @PostMapping("token")
    public String token() {
        return "<h1>token</h1>";
    }

//    @PostMapping("join")
//    public String join(@RequestBody User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setRoles("ROLE_USER");
//        userRepository.save(user);
//        return "회원가입완료";
//    }

    @PostMapping("join")
    public ResponseEntity<Map<String, Object>> join(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);

        // 응답으로 보낼 JSON 데이터를 준비
        Map<String, Object> response = new HashMap<>();
        response.put("message", "회원가입완료");
        response.put("userId", user.getId()); // 필요한 다른 정보 추가 가능

        // JSON 응답 반환
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }

}
