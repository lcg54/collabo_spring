package com.coffee.DTO;

import com.coffee.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String role;
    private LocalDate regDate;

    public static MemberResponse fromEntity(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getRole().name(),
                member.getRegDate()
        );
    }
}
