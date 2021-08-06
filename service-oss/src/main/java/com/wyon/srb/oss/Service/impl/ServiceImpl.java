package com.wyon.srb.oss.Service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;
import com.wyon.srb.oss.Service.FileService;
import com.wyon.srb.oss.util.OssProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@Service
public class ServiceImpl implements FileService {
    @Override
    public String upload(String module, MultipartFile file) {
        // 上传对象数据
        String originalFilename = file.getOriginalFilename();
//        int i = originalFilename.lastIndexOf(".");
//        String substring = originalFilename.substring(i);// 扩展名
        String filenameExtension = StringUtils.getFilenameExtension(originalFilename);


        // 公共参数
        String endpoint = OssProperties.ENDPOINT;// 深圳阿里云
        String accessKeyId = OssProperties.KEY_ID;//
        String accessScreate = OssProperties.KEY_SECRET;//密钥
        String bucketName = OssProperties.BUCKET_NAME;

        // ossClient
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessScreate);
        boolean b = ossClient.doesBucketExist(bucketName);

        // 文件名=桶名.endpoint/文件名
        String fileName = "模块" + "/" + "日期" + "/" + "uuid" + ".后缀";
        String data = new DateTime().toString("yyyy/MM/dd");
        String uuid = UUID.randomUUID().toString();
        fileName = module + "/" + data + "/" + uuid + "."+filenameExtension;


        if (!b) {
            // 如果桶不存在，创建桶
            ossClient.createBucket(bucketName);
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        // 如果桶存在则进行存储操作
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);

        ossClient.shutdown();

        String uploadUrl = "https://"+bucketName+"."+endpoint+"/"+ fileName;

        return uploadUrl;
    }
    }

