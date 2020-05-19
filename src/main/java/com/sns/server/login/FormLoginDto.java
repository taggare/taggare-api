package com.sns.server.login;

import com.sns.server.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormLoginDto {

    @Email(message = "이메일 형식에 맞게 정확히 입력해주세요.")
    private String email;

    @NotBlank(message = "패스워드를 정확히 입력해주세요.")
    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
}
