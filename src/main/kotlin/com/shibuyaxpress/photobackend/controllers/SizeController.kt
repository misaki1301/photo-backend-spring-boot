package com.shibuyaxpress.photobackend.controllers
import com.shibuyaxpress.photobackend.SizeRepository
import com.shibuyaxpress.photobackend.models.Size
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import com.shibuyaxpress.photobackend.response_models.SizeResponse

@RestController
@RequestMapping("/api/sizes")
@CrossOrigin(origins = ["*"])
class SizeController(private var sizeRepository: SizeRepository) {

    @GetMapping
    fun getAllSizes(): List<Size> = sizeRepository.findAll()

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun createSize(@RequestBody response: SizeResponse): Size {
        val size = Size(name = response.name, code = response.code)
        return sizeRepository.save(size)
    }


    @PutMapping("/{id}")
    fun updateSize(@PathVariable id:String, size: Size): Size = sizeRepository.save(size)

    @DeleteMapping("/{id}")
    fun deleteSize(@PathVariable id: String): Size {
        val document = sizeRepository.findById(id)
        var size: Size? = null
        if (document.isPresent) {
            size = document.get()
            size.isActive = false
            sizeRepository.save(size)
        }
        return ResponseEntity.badRequest().build<Size>().body!!
    }
}

