package com.example.demo.service

import com.example.demo.repository.DemoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import org.springframework.transaction.support.DefaultTransactionDefinition

@Service
class DemoService(
    private val operator: TransactionalOperator,
    private val repo: DemoRepository
) {

    private val log = LoggerFactory.getLogger(DemoService::class.java)

    suspend fun addRecords(): Unit = operator.executeAndAwait {
        log.info("Inserting records")
        repo.insert("a")
        repo.insert("b")
    }

    suspend fun getAll(): List<String> = repo.getAll()
}