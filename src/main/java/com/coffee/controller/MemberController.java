package com.coffee.controller;

import com.coffee.entity.Member;
import com.coffee.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody : 웹(react) 요청을 JSON으로 변환 → @RequestBody : 요청받은 JSON을 ResponseEntity로 반환
@RequiredArgsConstructor // final 키워드 또는 @NotNull 필드가 들어있는 식별자에 자동으로 생성자 주입
public class MemberController {
     private final MemberService memberService;

    @PostMapping("/member/signup") // @GetMapping은 select, @PostMapping은 create 용도로 사용
    public ResponseEntity<?> signup(@Valid @RequestBody Member newMember, BindingResult result){
        if (result.hasErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMsg);
        }

        // 이미 회원 존재 → 409 Conflict
        Member existingMember = memberService.findByEmail(newMember.getEmail());
        if (existingMember != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일입니다.");
        }

        // 회원가입 성공 → 201 Created
        memberService.insert(newMember);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입에 성공하였습니다. 로그인 페이지로 이동합니다.");
    }
}
