package com.sns.server.post;

import com.sns.server.common.ApiResponse;
import com.sns.server.security.SecurityAccount;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @PostMapping("/posts")
    @Secured("ROLE_USER")
    @CrossOrigin
    @ApiOperation(value = "글 작성")
    public ResponseEntity create(@ApiIgnore @AuthenticationPrincipal SecurityAccount securityAccount,
                                 @RequestBody PostDto.Create postDto) {

        postDto.setId(securityAccount.getUserId());

        postService.create(postDto);

        ApiResponse response = ApiResponse.builder()
                .message("글 작성을 완료하였습니다.")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.status(response.getStatus()).body(response);

    }
}
