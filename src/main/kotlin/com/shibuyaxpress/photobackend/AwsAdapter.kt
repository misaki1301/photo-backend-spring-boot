package com.shibuyaxpress.photobackend

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.net.URL
import javax.imageio.ImageIO

@Component
class AwsAdapter {

    private val logger: Logger? = LoggerFactory.getLogger(AwsAdapter::class.java)

    @Value("\${amazon.s3.bucket-name}")
    lateinit var bucketName: String

    @Autowired
    private lateinit var amazonS3Client: AmazonS3

    fun uploadBufferedImageToS3(image: BufferedImage, fileName: String, imageType: String): URL? {
        val outstream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outstream)
        val buffer = outstream.toByteArray()
        val inputStream = ByteArrayInputStream(buffer)
        val meta = ObjectMetadata()
        meta.contentType = imageType
        meta.contentLength = buffer.size.toLong()
        try {
            amazonS3Client.putObject(PutObjectRequest(bucketName, fileName, inputStream, meta)
                .withCannedAcl(CannedAccessControlList.PublicRead))
        } catch (exception: Exception) {
            throw RuntimeException("Error while uploading file with name $fileName")
        }
        return amazonS3Client.getUrl(bucketName, fileName)
    }

    fun storeObjectInS3(file: MultipartFile, fileName: String, contentType: String): URL? {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = contentType
        objectMetadata.contentLength = file.size
        try {
            amazonS3Client.putObject(PutObjectRequest(bucketName, fileName, file.inputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead))
        } catch (exception: Exception) {
            throw RuntimeException("Error while uploading file with name $fileName")
        }

        return amazonS3Client.getUrl(bucketName, fileName)
    }

    fun fetchObject(awsFileName: String): S3Object? {
        val s3Object: S3Object?
        try {
            s3Object = amazonS3Client.getObject(GetObjectRequest(bucketName, awsFileName))
        } catch (exception: Exception) {
            throw Exception("Error while streaming file")
        }
        return s3Object
    }

    fun deleteObject(key: String) {
        try {
            amazonS3Client.deleteObject(bucketName, key)
        } catch (exception: Exception) {
            logger!!.error("Error while deleting file")
        }
    }
}