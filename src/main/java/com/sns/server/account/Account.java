package com.sns.server.account;

import com.sns.server.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
@Getter
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

    private String password;

    private Gender gender;

    private String birth;

    private String tel;

    @CreationTimestamp
    @Column(updatable = false)
    private Date created;

    @LastModifiedDate // @LastModifiedBy
    @Column(updatable = false)
    private Date updated;

    @Builder
    public Account(String email, String firstName, String lastName, String password, Gender gender, String birth, String tel) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.tel = tel;
    }
}
