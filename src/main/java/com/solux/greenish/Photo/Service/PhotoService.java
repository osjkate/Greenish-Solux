package com.solux.greenish.Photo.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.solux.greenish.Photo.Domain.Photo;
import com.solux.greenish.Photo.Dto.PhotoResponseDto;
import com.solux.greenish.Photo.Dto.PresignedUrlDto;
import com.solux.greenish.Photo.Repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${amazon.aws.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Value("${amazon.aws.cloudfront.distribution-domain}")
    private String domain;


    // 포토 삭제
    @Transactional
    public void deletePhoto(Photo photo) {
        if (photo == null) return;
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, photo.getPhotoPath()));
    }


    // 포토 생성
    @Transactional
    public PhotoResponseDto createPhoto(PresignedUrlDto request) {
        String filePath = createPath(request.getPrefix(), request.getFileName());
        Photo photo = Photo.builder()
                .fileName(request.getFileName())
                .photoPath(filePath)
                .build();
        photoRepository.save(photo);
        return PhotoResponseDto.builder()
                .photoId(photo.getId())
                .url(getPreSignedUrl(filePath, HttpMethod.PUT)).build();
    }


    // PreSigne_ure 생성함수 (put, delete에 사용)
    public String getPreSignedUrl(String filePath, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket,
                filePath, httpMethod);
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName,
                                                                       HttpMethod httpMethod) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(httpMethod)
                        .withExpiration(getPresignedUrlExpiration());
        return generatePresignedUrlRequest;
    }

    private String createPath(String prefix, String fileName) {
        return String.format("%s/%s", prefix, fileName);
    }

    // CDN Url 생성함수
    public String getCDNUrl(String prefix, String imageUrl) {
        return "http://" + domain + "/" + prefix + "/" + imageUrl;
    }

    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
}
