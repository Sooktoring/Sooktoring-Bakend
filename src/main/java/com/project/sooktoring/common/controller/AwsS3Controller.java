package com.project.sooktoring.common.controller;

import com.project.sooktoring.common.utils.S3Uploader;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Api(tags = "AWS S3 테스트 API")
@RequiredArgsConstructor
@RequestMapping("/img")
@RestController
public class AwsS3Controller {

    private final S3Uploader s3Uploader;

    //이미지 업로드
    @PostMapping("/upload")
    public String uploadImg(@RequestPart("file") MultipartFile file) {
        return s3Uploader.uploadImg(file, "test");
    }

    //이미지 삭제
    @DeleteMapping("/delete")
    public String delete(@RequestBody Map<String, String> url) {
        s3Uploader.deleteImg(url.get("url"));
        return url.get("url"); //리턴된 url 접속 안 되는 것 확인
    }
}
