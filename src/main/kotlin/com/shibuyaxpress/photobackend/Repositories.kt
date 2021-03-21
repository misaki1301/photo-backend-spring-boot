package com.shibuyaxpress.photobackend

import com.shibuyaxpress.photobackend.models.Person
import com.shibuyaxpress.photobackend.models.Product
import com.shibuyaxpress.photobackend.models.ProductType
import com.shibuyaxpress.photobackend.models.User
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

@Repository
interface ProductRepository: MongoRepository<Product, String>

@Repository
interface ProductTypeRepository: MongoRepository<ProductType, String>

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findUserByUsername(username: String): User?
}

@Repository
interface PersonRepository: MongoRepository<Person, String>