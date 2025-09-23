package com.coffee.Entity;

import com.coffee.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDate regDate;

    @PrePersist
    protected void onCreate() {
        this.role = Role.USER;
        this.regDate = LocalDate.now();
    }
}
