package com.shibuyaxpress.photobackend

import com.shibuyaxpress.photobackend.models.*
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType
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

@Repository
interface OrderRepository: MongoRepository<Order, String>

@Repository
interface ColorRepository: MongoRepository<Color, String> {
    fun getAllByActiveTrue(): List<Color>
}

@Repository
interface SizeRepository: MongoRepository<Size, String>