package com.sns.server.account;

import com.sns.server.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Create {

        @NotBlank(message = "이메일을 정확히 입력해주세요.")
        private String email;

        @NotBlank(message = "영문 이름을 정확히 입력해주세요.")
        private String firstName;

        @NotBlank(message = "영문 성을 정확히 입력해주세요.")
        private String lastName;


        @NotBlank(message = "패스워드를 정확히 입력해주세요.")
        private String password;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @NotBlank(message = "생일 형식에 맞게 정확히 입력해주세요.")
        private String birth;

        @NotBlank(message = "전화번호 형식에 맞 정확히 입력해주세요.")
        private String tel;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Read {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private Gender gender;
        private String birth;
        private String tel;


        public static Read from(Account account) {
            return Read.builder()
                    .id(account.getId())
                    .email(account.getEmail())
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .gender(account.getGender())
                    .birth(account.getBirth())
                    .tel(account.getTel())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Update {

        @NotBlank(message = "이메일을 정확히 입력해주세요.")
        private String email;

        @NotBlank(message = "영문 이름을 정확히 입력해주세요.")
        private String firstName;

        @NotBlank(message = "영문 성을 정확히 입력해주세요.")
        private String lastName;


        @NotBlank(message = "패스워드를 정확히 입력해주세요.")
        private String password;


        @NotBlank(message = "전화번호 형식에 맞 정확히 입력해주세요.")
        private String tel;
    }

}
