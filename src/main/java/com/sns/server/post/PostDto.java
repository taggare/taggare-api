package com.sns.server.post;

import com.sns.server.image.Image;
import com.sns.server.love.Love;
import com.sns.server.reply.Reply;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PostDto {

    @Setter
    @Getter
    public static class PostCreateDto {
        private Long id;
        private Set<String> hasTags;
        private String title;
        private String content;
        List<MultipartFile> files;
    }

    @Getter
    public static class PostReadDto {
        private Long id;
        private Set<Reply> reply;
        private List<String> hashTags;
        private Set<Love> loves;
        private String title;
        private String content;
        private List<Image> images;
        private String created;

        public PostReadDto(Post post) {
            this.id = post.getId();
            this.reply = post.getReply();
            this.hashTags = post.getHashTags().stream().map(t -> t.getTag()).collect(Collectors.toList());
            this.loves = post.getLoves();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.images = post.getImages();
            this.created = post.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
