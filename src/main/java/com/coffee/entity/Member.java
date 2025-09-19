package com.coffee.entity;

import com.coffee.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity // 클래스를 Entity(db의 테이블에 대응하는 클래스)로 만들기
@Table(name="members") // 매핑할 테이블의 이름을 members로 지정
public class Member {
    @Id // 해당 필드를 primary key로 지정
    @GeneratedValue(strategy = GenerationType.AUTO) // 해당 필드의 값을 자동생성(=index)하도록 설정
    private Long id;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @Column(unique = true, nullable = false) // 중복 x, not null
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15자리 이내로 입력해주세요.")
    @Pattern(regexp = ".*[A-Z].*", message = "비밀 번호는 대문자 1개 이상을 포함해야 합니다.")
    @Pattern(regexp = ".*[!@#$%].*", message = "비밀 번호는 특수 문자 '!@#$%' 중 하나 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String address;

    @Enumerated(EnumType.STRING) // enum을 STRING 타입으로 DB에 저장 (기본 타입: ORDINAL(=index))
    private Role role;

    private LocalDate regDate;

    @PrePersist // insert 시점에 자동으로 호출 // @PreUpdate → update 시점에 호출
    protected void onCreate() {
        this.role = Role.USER;
        this.regDate = LocalDate.now();
    }
}
