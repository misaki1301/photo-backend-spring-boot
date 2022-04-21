package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.AwsAdapter
import com.shibuyaxpress.photobackend.ProductRepository
import com.shibuyaxpress.photobackend.ProductTypeRepository
import com.shibuyaxpress.photobackend.models.Product
import com.shibuyaxpress.photobackend.models.ProductType
import com.shibuyaxpress.photobackend.services.FirebaseStorage
import org.imgscalr.Scalr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.function.EntityResponse
import java.util.*
import javax.imageio.ImageIO

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/products")
class StoreController(private val productRepository: ProductRepository,
                      private val productTypeRepository: ProductTypeRepository) {

    @Autowired
    private lateinit var firebaseStorage: FirebaseStorage

    @GetMapping
    fun getProductsList(): List<Product> {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: String): ResponseEntity<Product> {
        val product = productRepository.findByIdOrNull(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/types")
    fun getTypeList(): ResponseEntity<MutableList<ProductType>> {
        return try {
            ResponseEntity.ok(productTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "name")))
        } catch (e: Exception) {
            ResponseEntity.noContent().build()
        }
    }

    @PostMapping("/types")
    fun createType(@RequestBody item: ProductType): ProductType {
        return productTypeRepository.save(item)
    }

    @PostMapping
    fun createProduct(@RequestPart image: MultipartFile,
                      @RequestPart(required = true) name: String,
                      @RequestPart(required = false) description: String?,
                      @RequestPart(required = true) price: String,
                      @RequestPart(required = true) type: String): ResponseEntity<Any> {
        var url = firebaseStorage.save(image)
        url = firebaseStorage.getImageURL(url)
        val thumb = createThumbForProduct(image)
        val desc = if (description.isNullOrEmpty()){
            ""
        } else {
            description
        }
        val productType = productTypeRepository.findByIdOrNull(type) ?: return ResponseEntity.notFound().build()
        val item = Product(null, name, desc, price.toDouble(), url, thumb, productType)
        val product = productRepository.save(item)
        return ResponseEntity.ok(product)
    }

    private fun createThumbForProduct(file: MultipartFile): String {
        val img = ImageIO.read(file.inputStream)
        val thumbnail = Scalr.resize(img, 250)
        return firebaseStorage.getImageURL(firebaseStorage.save(thumbnail, file.originalFilename!!))
    }
}