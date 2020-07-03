package com.sns.server.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class PostDto {
    @Setter
    @Getter
    public static class Create{
        private Long id;
        private Set<String> hasTags;
        private String title;
        private String content;
        List<MultipartFile> files;
    }
}
