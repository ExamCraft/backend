package net.examcraft.plugins

import net.examcraft.db.ExamQuestions
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun env(key: String): String? = System.getenv(key)

fun configureDatabases() {
    Database.connect(
        url = env("DB_URL") ?: "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = env("DB_USER") ?: "root",
        driver = env("DB_DRIVER") ?: "org.h2.Driver",
        password = env("DB_PASSWORD") ?: ""
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(ExamQuestions)
    }
}