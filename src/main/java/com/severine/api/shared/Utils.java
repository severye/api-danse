package com.severine.api.shared;

import java.io.File;

import org.springframework.data.domain.Sort;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class Utils {
	public static Sort getSort() {
		return new Sort(Sort.Direction.ASC, "name");
	}
	public static String savePicture(String bucketName, String domain, String newFileName, File file) {

        BasicAWSCredentials awsCrreds = new BasicAWSCredentials(S3Constants.CREDENTIAL_LOGIN, S3Constants.CREDENTIAL_PWD);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        		.withCredentials(new AWSStaticCredentialsProvider(awsCrreds))
                .withRegion(Regions.EU_CENTRAL_1) // The first region to try your request against
                .withForceGlobalBucketAccessEnabled(true) // If a bucket is in a different region, try again in the correct region
                .build();
        
        s3.putObject(new PutObjectRequest(bucketName, newFileName, file)
            	      .withCannedAcl(CannedAccessControlList.PublicRead));
        return domain+bucketName+"/"+newFileName;
	}
}
