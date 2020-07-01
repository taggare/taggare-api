package com.sns.server.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sns.server.exceptions.post.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ImageUploader {
    private static final Logger log = LoggerFactory.getLogger(ImageUploader.class);

    @Value("${cloudinary.env}")
    private String cloudinaryUrl;

    public List<Map> upload(List<MultipartFile> multipartFiles) throws Exception {
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        List<Map> files = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            files.add(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()));
            log.info("URL: " + files);
        }

        if(!files.isEmpty()) {
            return files;
        }

        throw new FileUploadException();
    }

}
