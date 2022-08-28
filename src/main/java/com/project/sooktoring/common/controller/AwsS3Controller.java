package com.project.sooktoring.common.controller;

import com.project.sooktoring.common.utils.S3Uploader;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(tags = "AWS S3 API")
@RequiredArgsConstructor
@RequestMapping("/image")
@RestController
public class AwsS3Controller {

    private final S3Uploader s3Uploader;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 후 해당 url 반환")
    @PostMapping
    public String uploadImg(@RequestPart("file") MultipartFile file) {
        return s3Uploader.uploadImg(file, "test");
    }

    @Operation(summary = "이미지 삭제", description = "반환된 url 접속 안 되는 것 확인")
    @DeleteMapping
    public String delete(@RequestBody Map<String, String> url) {
        s3Uploader.deleteImg(url.get("url"));
        return url.get("url");
    }
}
