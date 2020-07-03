package com.sns.server.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sns.server.exceptions.post.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Component
public class ImageUploader {
    private static final Logger log = LoggerFactory.getLogger(ImageUploader.class);

    @Value("${cloudinary.key}")
    private String cloudinaryKey;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        cloudinary = new Cloudinary(cloudinaryKey);
    }

    public String upload(MultipartFile file) {
        return convertImageUrl(file);
    }

    private String convertImageUrl(MultipartFile file) {
        try {
            return Optional.ofNullable(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url"))
                    .orElseThrow(FileUploadException::new)
                    .toString();
        } catch (IOException e) {
            throw new FileUploadException();
        }
    }
}
