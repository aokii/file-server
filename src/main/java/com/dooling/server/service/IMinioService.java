package com.dooling.server.service;

import java.io.InputStream;

/**
 * description: IMinioService
 *
 * @date: 2020/11/12 10:31 上午
 * @author: dooling
 */
public interface IMinioService {
    /**
     * 创建bucket
     *
     * @param bucket bucket
     */
    void creatBucket(String bucket);


    /**
     * 设置bucket策略
     *
     * @param bucket       bucket
     * @param policy       策略
     */
    void setBucketPolicy(String bucket, BucketPolicy policy);

    /**
     * 文件路劲上传文件
     *
     * @param bucket      bucket
     * @param objectKey   文件key
     * @param contentType 文件类型
     * @param filePath    文件路径
     * @return url
     */
    String uploadFile(String bucket, String objectKey, String contentType, String filePath);


    /**
     * 流式上传文件
     *
     * @param bucket      bucket
     * @param objectKey   文件key
     * @param contentType 文件类型
     * @param inputStream 文件输入流
     * @return url
     */
    String uploadInputStream(String bucket, String objectKey, String contentType, InputStream inputStream);


    /**
     * 获取文件签名url
     *
     * @param bucket    bucket
     * @param objectKey 文件key
     * @param expires   签名有效时间  单位秒
     * @return 文件签名地址
     */
    String getSignedUrl(String bucket, String objectKey, int expires);


    /**
     * 文件复制
     *
     * @param sourceBucket    初始bucket
     * @param sourceObjectKey 源文件key
     * @param bucket          目标bucket
     * @param objectKey       文件key
     * @return url
     */
    String copyFile(String sourceBucket, String sourceObjectKey, String bucket, String objectKey);

    /**
     * 删除文件
     *
     * @param bucket    bucket
     * @param objectKey 文件
     */
    void deleteFile(String bucket, String objectKey);
}