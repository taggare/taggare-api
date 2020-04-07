package com.sns.server.post;

import com.sns.server.account.Account;
import com.sns.server.hashtag.HashTag;
import com.sns.server.like.Like;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = HashTag.class)
    @JoinColumn(name = "hashtag_id")
    private Set<HashTag> hashTags = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Like.class)
    @JoinColumn(name = "like_id")
    private Set<Like> likes = new HashSet<>();
}
