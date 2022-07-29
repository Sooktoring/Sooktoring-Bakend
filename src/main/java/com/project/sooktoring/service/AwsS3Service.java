package com.project.sooktoring.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.sooktoring.exception.EmptyFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    //이미지 업로드
    public String uploadImg(MultipartFile file, String category) {
        if (file.isEmpty()) {
            throw new EmptyFileException("파일이 존재하지 않습니다.");
        }

        String fileName = category + UUID.randomUUID();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    //이미지 삭제
    public void deleteImg(String originImgUrl) {
        if (originImgUrl.equals("")) return;

        try {
            amazonS3.deleteObject(bucketName, originImgUrl.split("/")[3]);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }

}
