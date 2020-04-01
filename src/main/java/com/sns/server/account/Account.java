package com.sns.server.account;

import com.sns.server.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@Where(clause = "deleted IS NULL")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty
    private String password;

    private Gender gender;

    @NotEmpty
    private String birth;

    @NotEmpty
    private String tel;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate // @LastModifiedBy
    private LocalDateTime updated;

    private LocalDateTime deleted;

    @Builder
    public Account(String email, String firstName, String lastName,
                   String password, Gender gender, String birth,
                   String tel, LocalDateTime deleted) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
        this.deleted = deleted;
    }
}
