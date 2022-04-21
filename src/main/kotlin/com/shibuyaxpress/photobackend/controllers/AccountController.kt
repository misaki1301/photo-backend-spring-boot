package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.Account
import com.shibuyaxpress.photobackend.AccountRepository
import com.shibuyaxpress.photobackend.AwsAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/accounts")
class AccountController(private var accountRepository: AccountRepository) {
    @Autowired
    private lateinit var awsAdapter: AwsAdapter

    @GetMapping
    fun getAccounts(): MutableList<Account> {
        return accountRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"))
    }

    @PostMapping
    fun createAccount(@ModelAttribute file: MultipartFile, @RequestPart name: String, @RequestPart lastname: String, @RequestPart documentNumber: String): Account {
        val imageProfile = awsAdapter.storeObjectInS3(file, "test/${file.originalFilename!!}", file.contentType!!)
        val account = Account(null,name, lastname, documentNumber, null, imageProfile.toString())
        return accountRepository.save(account)
    }
}