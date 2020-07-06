package com.sns.server.post;

import com.sns.server.account.AccountService;
import com.sns.server.common.page.PageRequestDto;
import com.sns.server.hashtag.HashTag;
import com.sns.server.hashtag.HashTagRepository;
import com.sns.server.image.Image;
import com.sns.server.image.ImageRepository;
import com.sns.server.utils.ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AccountService accountService;
    private final HashTagRepository hashTagRepository;
    private final ImageRepository imageRepository;
    private final ImageUploader imageUploader;

    public Post create(PostDto.PostCreateDto postDto) {
        List<HashTag> hasTags = hashTagRepository.saveAll(
                postDto.getHasTags()
                .stream().map(t -> HashTag.builder().tag(t).build())
                .collect(Collectors.toSet())
        );
        List<Image> images =  imageRepository.saveAll(
                postDto.getFiles().stream()
                .map(f -> Image.builder().url(imageUploader.upload(f)).build())
                .collect(Collectors.toList())
        );
        return postRepository.save(
                Post.builder()
                .account(accountService.get(postDto.getId()))
                .hashTags(hasTags)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .images(images)
                .build()
        );
    }

    @Transactional(readOnly = true)
    public Page<PostDto.PostReadDto> get(final PageRequestDto pageRequestDto) {
        return postRepository.findAll(pageRequestDto.of()).map(PostDto.PostReadDto::new);
    }
}
