package com.shibuyaxpress.photobackend

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {
    @Value("\${amazon.s3.access-key}")
    lateinit var Id: String

    @Value("\${amazon.s3.secret-key}")
    lateinit var key: String

    @Value("\${amazon.s3.endpoint}")
    lateinit var endpoint: String

    @Bean
    fun storageS3Client(): AmazonS3 = AmazonS3ClientBuilder
        .standard()
        .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, "nyc3"))
        .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(Id,key))).build()
}