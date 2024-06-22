package net.examcraft.routes.v1.exam

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.examcraft.db.ExamQuestions
import net.examcraft.db.structures.toExamQuestion
import net.examcraft.handler.gen.exam.generateExam
import net.examcraft.routes.v1.responses.FailResponse
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andIfNotNull
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

data class ExamParameters(
    val questions: Int,
    val qualification: String,
    val subject: String,
    val examSeries: String?,
    val paper: List<String>,
    val tags: List<String>
)

fun Route.generateExamV1() {
    post("/generate") {
        val parameters = call.receive<ExamParameters>()
        val questions = transaction {
            ExamQuestions.selectAll()
                .where {
                    (ExamQuestions.qualification eq parameters.qualification) and (ExamQuestions.subject eq parameters.subject) andIfNotNull (parameters.examSeries?.let { series -> ExamQuestions.examSeries eq series }) andIfNotNull (parameters.paper.takeIf { it.isNotEmpty() }
                        ?.let { papers -> ExamQuestions.paper inList papers })
                }.filter { result ->
                    parameters.tags.takeIf { it.isNotEmpty() }
                        ?.let { result[ExamQuestions.tags].any { tag -> parameters.tags.contains(tag) } } ?: false
                }.random(parameters.questions)
                .map { it.toExamQuestion() }
        }
        if (questions.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, FailResponse("There are no questions that match this criteria!"))
        }

        val exam = generateExam(questions)

        call.respondBytes(exam, contentType = ContentType.defaultForFileExtension("pdf"))
    }
}

private fun <T> List<T>.random(amount: Int): List<T> {
    if (amount > size) {
        return this
    } else {
        val newList = ArrayList<T>()
        repeat(amount) {
            var added = false
            while (!added) {
                val random = random()
                if (!newList.contains(random)) {
                    newList.add(random)
                    added = true
                }
            }
        }

        return newList
    }
}