package com.sns.server.account;

import com.sns.server.enums.Gender;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String password;
    private Gender gender;
    private String birth;
    private String tel;
    @CreatedDate
    private Date created;
    @LastModifiedDate // @LastModifiedBy
    private Date updated;
}
