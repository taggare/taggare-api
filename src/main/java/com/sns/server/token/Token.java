package com.sns.server.token;

import com.sns.server.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Where(clause = "deleted IS NULL")
@Getter
@Setter

@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @Column(name = "access_token", length = 1000)
    private String accessToken;

    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    private LocalDateTime deleted;

    @Builder
    public Token(Account account, String accessToken, String refreshToken) {
        this.account = account;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
