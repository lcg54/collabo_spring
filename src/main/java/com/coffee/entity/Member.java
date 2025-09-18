package com.coffee.entity;

import com.coffee.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity // 클래스를 Entity(db의 테이블에 대응하는 클래스)로 만들기
@Table(name="members") // 매핑할 테이블의 이름을 members로 지정
public class Member {
    @Id // 해당 필드를 primary key로 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // 해당 필드의 값을 자동생성(=index)하도록 설정
    private Long id;
    private String name;
    @Column(unique = true, nullable = false) // 중복 x, not null
    private String email;
    private String password, address;
    @Enumerated(EnumType.STRING) // enum을 STRING 타입으로 DB에 저장 (기본 타입: ORDINAL(=index))
    private Role role;
    private LocalDate regDate;

//    @PrePersist // insert 시점에 자동으로 호출 // @PreUpdate → update 시점에 호출
//    protected void onCreate() {
//        this.regDate = LocalDate.now();
//    }
}
