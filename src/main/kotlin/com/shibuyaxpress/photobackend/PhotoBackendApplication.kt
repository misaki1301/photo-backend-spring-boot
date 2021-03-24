package com.shibuyaxpress.photobackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


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

