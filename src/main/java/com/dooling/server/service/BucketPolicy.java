package com.dooling.server.service;

/**
 * description: BucketPolicy
 *
 * @date: 2020/11/12 3:02 下午
 * @author: dooling
 */
public enum BucketPolicy {
    /**
     * 只读策略
     */
    READ_ONLY("READ_ONLY", PolicyConfig.READ_ONLY_CONFIG_JSON),
    /**
     * 只写策略
     */
    WRITE_ONLY("WRITE_ONLY", PolicyConfig.WRITE_ONLY_CONFIG_JSON),
    /**
     * 读写策略
     */
    READ_WRITE("READ_WRITE", PolicyConfig.READ_WRITE_CONFIG_JSON);


    private String tag;
    private String configJson;

    BucketPolicy(String tag, String configJson) {

    }

    class PolicyConfig {
        /**
         * 桶占位符
         */
        private static final String BUCKET_PARAM = "${bucket}";

        /**
         * bucket权限-只读
         */
        private static final String READ_ONLY_CONFIG_JSON = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
        /**
         * bucket权限-只读
         */
        private static final String WRITE_ONLY_CONFIG_JSON = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
        /**
         * bucket权限-读写
         */
        private static final String READ_WRITE_CONFIG_JSON = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetBucketLocation\",\"s3:ListBucket\",\"s3:ListBucketMultipartUploads\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\"],\"Resource\":[\"arn:aws:s3:::" + BUCKET_PARAM + "/*\"]}]}";
    }

    public String getTag() {
        return tag;
    }


    public String getConfigJson() {
        return configJson;
    }

    /**
     * 获取配置
     *
     * @param bucket
     * @return
     */
    public String getConfig(String bucket) {
        return configJson.replace(PolicyConfig.BUCKET_PARAM, bucket);
    }

    /**
     * 根据标签获取策略配置
     *
     * @param policyTag
     * @return
     */
    public BucketPolicy getPolicy(String policyTag) {
        BucketPolicy[] values = values();
        for (BucketPolicy value : values) {
            if (value.getTag().equalsIgnoreCase(policyTag)) {
                return value;
            }
        }
        return null;
    }
}
