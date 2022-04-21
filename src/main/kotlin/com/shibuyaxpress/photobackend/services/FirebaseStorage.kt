package com.shibuyaxpress.photobackend.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Acl
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.StorageClient
import com.shibuyaxpress.photobackend.configs.FirebaseConfigProperties
import com.shibuyaxpress.photobackend.services.interfaces.StorageService
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage

@Service
class FirebaseStorage(private val firebaseConfig: FirebaseConfigProperties): StorageService {

    init {
        try {
            val serviceAccount = ClassPathResource("firebase-admin.json")
            val options: FirebaseOptions = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.inputStream))
                    .setStorageBucket(firebaseConfig.bucketName)
                    .build()
            FirebaseApp.initializeApp(options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getImageURL(name: String): String = "${firebaseConfig.imageUrl}$name"

    override fun save(file: MultipartFile): String {
        val bucket = StorageClient.getInstance().bucket()
        val name = generateFilename(file.originalFilename!!)
        bucket.create(name, file.bytes, file.contentType, Bucket.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ))
        return name
    }

    override fun save(image: BufferedImage, originalFileName: String): String {
        val bytes = getBytesArray(image, getExtension(originalFileName)!!)
        val bucket = StorageClient.getInstance().bucket()
        val name = generateFilename(originalFileName)
        bucket.create(name, bytes, "image/jpeg", Bucket.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ))
        return name
    }

    override fun delete(name: String) {
        val bucket = StorageClient.getInstance().bucket()
        if (StringUtils.isEmpty(name)){
            throw Exception("Invalid filename")
        }
        val blob = bucket.get(name)
        if (blob != null) {
            bucket.delete()
        }
    }

}