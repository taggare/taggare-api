package com.sns.server.hashtag;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sns.server.post.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "hash_tags")
@Where(clause = "deleted IS NULL")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "hashTags")
    @JsonBackReference
    private Set<Post> post;

    private String tag;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    private LocalDateTime deleted;
}
