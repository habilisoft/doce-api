package com.habilisoft.doce.api.email.services;

import com.amazonaws.auth.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Created on 2019-04-08.
 */
@Component
@ConditionalOnProperty(name = "mail.sender", havingValue = "SES")
public class SESAwsCredentials implements AWSCredentials {

    private final String accessKey;
    private final String secretKey;

    public SESAwsCredentials(
            @Value("${cloud.aws.credentials.accessKey:\"\"}") String accessKey,
            @Value("${cloud.aws.credentials.secretKey:\"\"}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    @Override
    public String getAWSAccessKeyId() {
        return accessKey;
    }

    @Override
    public String getAWSSecretKey() {
        return secretKey;
    }
}
