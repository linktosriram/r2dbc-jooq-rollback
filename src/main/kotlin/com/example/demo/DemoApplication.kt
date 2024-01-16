package com.example.demo

import com.example.demo.service.DemoService
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@SpringBootApplication
class DemoApplication {

    @Bean
    fun runner(service: DemoService): CommandLineRunner = CommandLineRunner {
        runBlocking {
            try {
                service.addRecords()
            } catch (e: Exception) {
                println("Error while calling addRecords: ${e.message}")
            }
            println("All IDs in DB : ${service.getAll()}")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
