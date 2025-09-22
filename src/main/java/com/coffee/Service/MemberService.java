package com.coffee.Service;

import com.coffee.Entity.Member;
import com.coffee.Repository.MemberRepository;
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

    public Member findByEmailAndPassword(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
