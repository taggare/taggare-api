package com.sns.server.account;

import com.sns.server.enums.Gender;

import javax.validation.constraints.Min;

public class UserDTO {
    public static class Create {
        private String name;
        @Min(value = 8, message = "비밀번호는 최소 8자리 이상입니다.")
        private String password;
        private Gender gender;
        private String birth;
        private String tel;
    }
}
