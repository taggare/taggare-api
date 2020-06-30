package com.sns.server.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
public class ImageUploader {
    private static final Logger log = LoggerFactory.getLogger(ImageUploader.class);

    @Value("${cloudinary.env}")
    private String cloudinaryUrl;

    public Map upload(MultipartFile file) throws Exception {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        if(!result.isEmpty()) {
            log.info("URL: " + result.get("url"));
            return result;
        }
        throw new RuntimeException("파일 업로드 에러 발생");
    }

}
