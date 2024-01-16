package com.example.demo.repository

import com.example.demo.generated.tables.references.DEMO_TABLE
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class DemoRepository(private val db: DSLContext) {

    suspend fun insert(idempotencyId: String) {
        if (idempotencyId == "b") {
            throw RuntimeException("error while inserting")
        }

        db.insertInto(DEMO_TABLE)
            .set(DEMO_TABLE.ID, idempotencyId)
            .awaitSingle()
    }

    suspend fun getAll(): List<String> {
        return db.selectFrom(DEMO_TABLE)
            .asFlow()
            .map { it.id!! }
            .toList()
    }
}