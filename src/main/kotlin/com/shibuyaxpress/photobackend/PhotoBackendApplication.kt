package com.shibuyaxpress.photobackend

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@SpringBootApplication
class PhotoBackendApplication

fun main(args: Array<String>) {
	runApplication<PhotoBackendApplication>(*args)
}


//Spring Data
//MySQL PostgreSQL
//MongoDB => using this currently for incredible performance
//Cloud . Firestore, Datastore(NoSQL), Cloud Spanner
@Document
data class Photo(
	@Id
	var id: String? = null,
	var uri: String? = null,
	var label: String? = null
)

@Document(collection = "issues")
data class Issue(
	@Id
	var id: String? = null,
	var description: String? = null,
	var severity: String? = null,
	var assignee: String? = null,
	@JsonFormat(pattern = "yyyy/MM/dd")
	var createdAt: Date = Date()
)

// here we generate a crud operation with repository
@Repository
interface IssueRepository: MongoRepository<Issue, String>

@RestController
@CrossOrigin(origins = arrayOf("*"))
class HelloController(private val issueRepository: IssueRepository) {
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

	@PostMapping("/photov2")
	fun uploadToGallery(@ModelAttribute file: MultipartFile): String {

	}

}

