package com.sns.server.reply;

import com.sns.server.account.Account;
import com.sns.server.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Where(clause = "deleted IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String content;
}
