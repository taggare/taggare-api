package com.sns.server.hashtag;


import com.sns.server.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hashtag")
@Where(clause = "deleted IS NULL")
@Getter
@Setter
@NoArgsConstructor
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    private String tag;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private LocalDateTime deleted;
}
