package com.coffee.repository;

import com.coffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity 이름, 해당 Entity의 primary key의 타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 쿼리 메소드(Query Method) → 메서드 이름만으로 데이터베이스 쿼리를 자동 생성. Spring Data JPA에서 사용
    // findBy + 조건자 (And, Or, Between, GreaterThan, LessThan, Like)
    Member findByEmail(String email); // 이메일에 해당하는 멤버가 있으면 반환, 없으면 null 반환
}
