package com.shibuyaxpress.photobackend.services.interfaces

import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import javax.imageio.ImageIO

interface StorageService {
    fun getImageURL(name: String): String
    fun save(file: MultipartFile): String
    fun save(image: BufferedImage, originalFileName: String): String
    fun delete(name: String)

    fun getExtension(filename: String): String? {
        return StringUtils.getFilenameExtension(filename)
    }

    fun generateFilename(originalFileName: String): String {
        val extension = getExtension(originalFileName)
        return "${UUID.randomUUID().toString()}.$extension"
    }

    fun getBytesArray(image: BufferedImage, format: String): ByteArray {
        val baos = ByteArrayOutputStream()
        try {
            ImageIO.write(image, format, baos)
            baos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            throw e
        } finally {
            baos.close()
        }

    }
}