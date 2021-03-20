package com.shibuyaxpress.photobackend

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.ArrayList

//Spring Data
//MySQL PostgreSQL
//MongoDB => using this currently for incredible performance
//Cloud . Firestore, Datastore(NoSQL), Cloud Spanner

@Document(collection = "bank_accounts")
data class BankAccount(
    @Id
    var id: String? = null,
    var bankName: String? = null,
    var accountNumber: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null
)

@Document(collection = "bank_transactions")
data class BankTransaction(
    @Id
    var id: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var originPerson: Account? = null,
    var destinationPerson: Account? = null,
    var amount: Double? = 0.0
)

@Document(collection = "accounts")
data class Account(
    @Id
    var id: String? = null,
    var name: String? = null,
    var lastname: String? = null,
    var documentNumber: String? = null,
    var bankAccount: ArrayList<BankAccount>? = ArrayList(),
    var imageProfile: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var updatedAt: LocalDateTime? = null
)

@Document(collection = "photos")
data class Photo(
    @Id
    var id: String? = null,
    var uri: String? = null,
    var thumbUrl: String? = null,
    var label: String? = null,
    var fileName: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now()
)

@Document(collection = "issues")
data class Issue(
    @Id
    var id: String? = null,
    var description: String? = null,
    var severity: String? = null,
    var assignee: String? = null,
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "America/Lima")
    var createdAt: LocalDateTime = LocalDateTime.now()
)