package com.sns.server.account;

import com.sns.server.enums.Gender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Create {

        private String email;

        @NotEmpty
        private String firstName;

        @NotEmpty
        private String lastName;


        @NotEmpty
        private String password;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @NotBlank
        private String birth;

        @NotBlank
        private String tel;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {
        @NotBlank
        @Min(value = 8, message = "비밀번호는 최소 8자리 이상입니다.")
        private String password;

        @NotBlank
        private String tel;
    }
}
