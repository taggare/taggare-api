package com.sns.server.post;

import com.sns.server.common.ApiResponse;
import com.sns.server.utils.ImageUploader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    private final ImageUploader imageUploader;

    @PostMapping("/post/upload")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @CrossOrigin
    @ApiOperation(value = "파일업로드")
    public ResponseEntity upload(@RequestParam("file") List<MultipartFile> multipartFiles) throws Exception {
        ApiResponse response = ApiResponse.builder()
                .list(imageUploader.upload(multipartFiles))
                .message("이미지 업로드 완료.")
                .status(HttpStatus.OK)
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
