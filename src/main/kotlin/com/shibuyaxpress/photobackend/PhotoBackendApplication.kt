package com.shibuyaxpress.photobackend

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@SpringBootApplication
class PhotoBackendApplication

fun main(args: Array<String>) {
	runApplication<PhotoBackendApplication>(*args)
}

@RestController
@CrossOrigin(origins = ["*"])
class HelloController(private val issueRepository: IssueRepository, private val photoRepository: PhotoRepository) {
	@Autowired
	private lateinit var awsAdapter: AwsAdapter

	@GetMapping("/")
	fun hello() = "Hello!"

	@PostMapping("/photo")
	fun uploadPhoto(@ModelAttribute file: MultipartFile): String {
		val url = awsAdapter
			.storeObjectInS3(file, "test/${file.originalFilename!!}", file.contentType!!)

		return url.toString()
	}

	@GetMapping("/issues")
	fun getIssues(): MutableList<Issue> {
		return issueRepository.findAll()
	}

}

