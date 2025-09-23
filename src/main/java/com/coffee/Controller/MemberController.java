package com.coffee.Controller;

import com.coffee.DTO.LoginRequest;
import com.coffee.DTO.SignupRequest;
import com.coffee.Entity.Member;
import com.coffee.Service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController // @Controller + @ResponseBody → 웹(react) 요청을 JSON으로 변환 / @RequestBody → 요청받은 JSON을 ResponseEntity로 반환
@RequiredArgsConstructor // final 키워드 또는 @NotNull 필드가 들어있는 식별자에 자동으로 생성자 주입
public class MemberController {
     private final MemberService memberService;

    @PostMapping("/member/signup") // 주소창에 입력정보가 노출되지 않게 post (노출되는 건 get)
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest, BindingResult bindingResult){
        // validation 통과 실패 → 400 Bad Request + Map(필드명, 메시지) 반환
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        // 이메일 중복 → 409 Conflict + Map 반환
        Member existingEmail = memberService.findByEmail(signupRequest.getEmail());
        if (existingEmail != null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("email", "이미 존재하는 이메일입니다.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
        }
        // DTO에서 엔티티로 수동 매핑하여 insert
        Member member = new Member();
        member.setName(signupRequest.getName());
        member.setEmail(signupRequest.getEmail());
        member.setPassword(signupRequest.getPassword());
        member.setAddress(signupRequest.getAddress());
        memberService.insert(member);
        // 회원가입 성공 → 201 Created 반환
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입에 성공하였습니다. 로그인 페이지로 이동합니다.");
    }

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        }
        // 이메일 없음 → 404 NOT_FOUND
        Member existingEmail = memberService.findByEmail(loginRequest.getEmail());
        if (existingEmail == null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("email", "이메일이 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
        }
        // 비밀번호 불일치 -> 401 UNAUTHORIZED
        Member rightPassword = memberService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if (rightPassword == null) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("password", "비밀번호가 일치하지 않습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMap);
        }
        // 로그인 성공 → 200 OK + Map(메시지, 회원정보(role, email)) 전달
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("message", rightPassword.getName() + "님, 환영합니다!");
        userMap.put("user", Map.of("role",rightPassword.getRole(), "email", rightPassword.getEmail()));
        return ResponseEntity.status(HttpStatus.OK).body(userMap);
    }
}
