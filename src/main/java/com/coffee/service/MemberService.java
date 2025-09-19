package com.coffee.service;

import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void insert(Member newMember) {
        memberRepository.save(newMember); // Repository에서 insert 작업은 save() 사용
    }
}
