package com.iris.system.bean;

/*
 * In Spring, the objects that form the backbone of your application and that are
 * managed by the Spring IoC container are called beans.
 A bean is an object that is instantiated, assembled, and otherwise managed
 * by a Spring IoC container.
 */

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonCloudWatchBean {

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonCloudWatch amazonCLoudWatch() {
        return AmazonCloudWatchClient.builder()
                        .withCredentials(this.awsCredentialsProvider)
                        .withRegion(this.region)
                        .build();

    }
}
