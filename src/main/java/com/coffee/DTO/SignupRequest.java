package com.coffee.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 8, max = 15, message = "비밀번호는 8 ~ 15자 이내로 입력해주세요.")
    @Pattern(regexp = ".*[A-Z].*", message = "비밀번호는 대문자 1개 이상을 포함해야 합니다.")
    @Pattern(regexp = ".*[!@#$%].*", message = "비밀번호는 특수 문자 '!@#$%' 중 하나 이상을 포함해야 합니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 사항입니다.")
    private String address;
}

