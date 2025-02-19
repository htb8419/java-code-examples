package org.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@SpringBootTest
public class S3UnitTest {

    @Autowired
    S3Client s3Client;

    @Test
    public void givenS3Client_whenGetBuckets_thenSuccess() {
        ListBucketsResponse listBucketsResponse = s3Client.listBuckets();
        List<Bucket> allBuckets = listBucketsResponse.buckets();
        Assertions.assertNotNull(allBuckets);
    }
    @Test
    public void givenFile_whenSave_thenSuccess() throws FileNotFoundException {
        File imageFile = ResourceUtils.getFile("classpath:Mercedes-E60-AMG-1-767x511.jpg");
        //ByteArrayInputStream inputStream=new ByteArrayInputStream(imageFile.b)
        PutObjectResponse objectResponse = s3Client.putObject(PutObjectRequest.builder()
                        .bucket("ht-mys3")
                        .key("benze.jpg")
                        .build(),
                RequestBody.fromString("tttte"));
        Assertions.assertNotNull(objectResponse);

    }
}
