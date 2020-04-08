package com.sns.server.post;

import com.sns.server.account.Account;
import com.sns.server.hashtag.HashTag;
import com.sns.server.history.History;
import com.sns.server.love.Love;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@Where(clause = "deleted IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private History history;

    @OneToMany(mappedBy = "post")
    private Set<HashTag> hashTags = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<Love> loves = new HashSet<>();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private LocalDateTime deleted;
}
