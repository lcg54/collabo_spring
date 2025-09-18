package com.coffee.repository;

import com.coffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity 이름, 해당 Entity의 primary key의 타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
}
