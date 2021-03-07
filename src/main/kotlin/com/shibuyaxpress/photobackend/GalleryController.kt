package com.shibuyaxpress.photobackend

import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.imageio.ImageIO

@RestController
@CrossOrigin(origins = ["*"])
class GalleryController(private val photoRepository: PhotoRepository) {
    @Autowired
    private lateinit var awsAdapter: AwsAdapter

    @GetMapping("/photos")
    fun getPhotoList(): MutableList<Photo> {

        return photoRepository
            .findAll(Sort.by(Sort.Direction.ASC, "createdAt"))
    }

    @PostMapping("/photos")
    fun uploadToGallery(@ModelAttribute file: MultipartFile, @RequestPart(required = false) name: String?): Photo {
        val url = awsAdapter.storeObjectInS3(file, "test/${file.originalFilename!!}", file.contentType!!)
        val photoName: String = if (name != null && name.isNotEmpty()) {
            name
        } else {
            file.originalFilename.toString()
        }
        val img = ImageIO.read(file.inputStream)
        val thumbnail = Scalr.resize(img, 250)
        val thumb = awsAdapter.uploadBufferedImageToS3(thumbnail, "test/thumbnails/${file.originalFilename!!}", file.contentType!!)
        val photo = Photo(null, url.toString(), thumb.toString(), photoName, file.originalFilename!!)
        return photoRepository.save(photo)

    }

    @DeleteMapping("/photos/{id}")
    fun deletePhoto(@PathVariable("id") id: String) {
        val item: Photo = photoRepository.findByIdOrNull(id = id)!!
        val image = awsAdapter.fetchObject(item.fileName!!)
        awsAdapter.deleteObject(image!!.key)
        return photoRepository.deleteById(id)
    }
}