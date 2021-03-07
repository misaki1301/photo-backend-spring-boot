package com.shibuyaxpress.photobackend

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

// here we generate a crud operation with repository
@Repository
interface IssueRepository: MongoRepository<Issue, String>

@Repository
interface PhotoRepository: MongoRepository<Photo, String>

@Repository
interface AccountRepository: MongoRepository<Account, String>

@Repository
interface BankAccountRepository: MongoRepository<BankAccount, String>

@Repository
interface BankTransactionRepository: MongoRepository<BankTransaction, String>
