package com.solux.greenish.Photo.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${amazon.aws.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    // 업로드 할 때 preSignedUrl 생성
    @Transactional
    public PhotoResponseDto getPreSignedUrl(PresignedUrlDto request) {
        //String prefix, String fileName
        String photoPath = createPath(String.valueOf(request.getPrefix()), request.getFileName());



        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, photoPath, HttpMethod.GET);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        Photo photo = Photo.builder()
                .photoPath(photoPath)
                .fileName(request.getFileName())
                .build();

        photoRepository.save(photo);

        return PhotoResponseDto.builder()
                .photoId(photo.getId())
                .url(url.toString())
                .build();
    }

    public PhotoResponseDto generatePreSignedDto(Long prefix, String fileName) {
        return getPreSignedUrl(PresignedUrlDto.builder()
                .prefix(prefix)
                .fileName(fileName)
                .build());

    }

    // 포토 삭제
    @Transactional
    public void deletePhoto(Photo photo) {
        if (photo == null) return;
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, photo.getPhotoPath()));

        photoRepository.delete(photo);
    }


    // photo_id에서 조회
    @Transactional(readOnly = true)
    public PhotoResponseDto getFilePath(Photo photo) {
        if (photo == null) return null;

        String photoPath = photo.getPhotoPath();
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, photoPath)
                .withMethod(HttpMethod.GET);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return PhotoResponseDto.builder()
                .photoId(photo.getId())
                .url(url.toString())
                .build();
    }

    /**
     * 파일 업로드용(PUT) presigned url 생성
     * @param bucket 버킷 이름
     * @param fileName S3 업로드용 파일 이름
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(httpMethod)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    /**
     * presigned url 유효 기간 설정
     * @return 유효기간
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    /**
     * 파일 고유 ID를 생성
     * @return 36자리의 UUID
     */
    private String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 파일의 전체 경로를 생성
     * @param prefix 디렉토리 경로
     * @return 파일의 전체 경로
     */
    private String createPath(String prefix, String fileName) {
        String fileId = createFileId();
        return String.format("%s/%s", prefix, fileId + fileName);
    }
}