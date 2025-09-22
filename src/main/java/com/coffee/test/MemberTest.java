package com.coffee.test;

import com.coffee.constant.Role;
import com.coffee.Entity.Member;
import com.coffee.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest // 이 클래스는 간단한 테스트를 위한 용도로 사용합니다.
public class MemberTest {

    @Autowired // 의존성 주입(DI, Dependency Injection) → 객체를 자동으로 주입
    private MemberRepository memberRepository; // 기본값은 null이므로 객체 주입이 필요
    // 근데 다음과 같이 생성자를 사용하는걸 더 추천함.
    // private final MemberRepository memberRepository;
    //
    // public MemberService(MemberRepository memberRepository) {
    //     this.memberRepository = memberRepository;
    // }

    @Test // 유닛(소단위)테스트
    public void insertMemberList(){
        Member mem01 = new Member();
        mem01.setName("관리자");
        mem01.setEmail("admin@naver.com");
        mem01.setPassword("Admin@123");
        mem01.setAddress("마포구 공덕동");
        mem01.setRole(Role.ADMIN);
        mem01.setRegDate(LocalDate.now());

        memberRepository.save(mem01); // save = insert
        System.out.println("----------------------");

        Member mem02 = new Member();
        mem02.setName("유영석");
        mem02.setEmail("bluesky@naver.com");
        mem02.setPassword("Bluesky@456");
        mem02.setAddress("용산구 이태원동");
        mem02.setRole(Role.USER);
        mem02.setRegDate(LocalDate.now());
        memberRepository.save(mem02) ;
        System.out.println("----------------------");

        Member mem03 = new Member();
        mem03.setName("곰돌이");
        mem03.setEmail("gomdori@naver.com");
        mem03.setPassword("Gomdori@789");
        mem03.setAddress("동대문구 휘경동");
        mem03.setRole(Role.USER);
        mem03.setRegDate(LocalDate.now());
        memberRepository.save(mem03) ;
        System.out.println("----------------------");
    }
}
