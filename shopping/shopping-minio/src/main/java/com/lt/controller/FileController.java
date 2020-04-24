package com.lt.controller;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.policy.PolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileController {

    @Value("${minio.endpoint}")
    private String endpoint;  // http://192.168.0.102:9000 #MinIO服务所在地址
    @Value("${minio.bucketName}")
    private String  bucketName ;//: mall #存储桶名称
    @Value("${minio.accessKey}")
    private String accessKey; //: minioadmin #访问的key
    @Value("${minio.secretKey}")
    private String  secretKey;//: minioadmin #访问的秘钥

    @PostMapping("/uploadFile")
    @CrossOrigin(value = "*")
    public Result uploadFile(MultipartFile file){
        try {
            MinioClient minioClient = new MinioClient(endpoint,accessKey,secretKey);
            boolean isExists = minioClient.bucketExists(bucketName);
            if (isExists){
                System.out.println("Bucket already exists");
            }else {
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName,"*.*", PolicyType.NONE);
            }
            String fileName = file.getOriginalFilename();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String objectName = dateFormat.format(new Date() )+ "/" + fileName;

            //将存储对象放到存储桶中
            minioClient.putObject(bucketName,objectName,file.getInputStream(),file.getContentType());
            System.out.println("文件上传成功");
            //获取图片url
            String objectUrl = minioClient.getObjectUrl(bucketName, objectName);
            System.out.println(objectUrl);
            return new Result(true,objectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }

    }
}
