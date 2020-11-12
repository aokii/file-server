package com.dooling.server.service.impl;

import com.dooling.server.service.BucketPolicy;
import com.dooling.server.service.IMinioService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author dooling
 * description: MinioServiceImpl
 */
@Service
public class MinioServiceImpl implements IMinioService {

    private final MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;

    public MinioServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * 文件url
     *
     * @param bucket 桶
     * @return url
     */
    private String getObjectUrl(String bucket, String objectKey) {
        return String.format("%s/%s/%s", endpoint, bucket, objectKey);
    }

    @Override
    public void creatBucket(String bucket) {
        try {
            // 判断桶是否存在
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                // 新建桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (MinioException |
                NoSuchAlgorithmException |
                IOException |
                InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBucketPolicy(String bucket, BucketPolicy policy) {
        try {
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket).config(policy.getConfig(bucket)).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public String uploadFile(String bucket, String objectKey, String contentType, String filePath) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(objectKey).filename(filePath).contentType(contentType).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getObjectUrl(bucket, objectKey);
    }

    @Override
    public String uploadInputStream(String bucket, String objectKey, String contentType, InputStream inputStream) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectKey).stream(inputStream, inputStream.available(), -1).contentType(contentType).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getObjectUrl(bucket, objectKey);
    }

    @Override
    public String getSignedUrl(String bucket, String objectKey, int expires) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucket).object(objectKey).expiry(expires).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public String copyFile(String sourceBucket, String sourceObjectKey, String bucket, String objectKey) {
        CopySource source = CopySource.builder().bucket(sourceBucket).object(sourceObjectKey).build();
        try {
            minioClient.copyObject(CopyObjectArgs.builder().bucket(bucket).object(objectKey).source(source).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return getObjectUrl(bucket, objectKey);
    }

    @Override
    public void deleteFile(String bucket, String objectKey) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectKey).build());
        } catch (MinioException |
                InvalidKeyException |
                IOException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
