package net.examcraft.routes.v1.exam

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.examcraft.db.ExamQuestions
import net.examcraft.routes.v1.responses.ParameterResponse
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.examTagsV1() {
    post("/qualifications") {
        val qualifications =
            transaction { ExamQuestions.selectAll().map { it[ExamQuestions.qualification] }.distinct() }
        call.respond(ParameterResponse(qualifications, "Successfully located all qualifications"))
    }

    post("/subjects") {
        val examSeries = transaction { ExamQuestions.selectAll().map { it[ExamQuestions.subject] }.distinct() }
        call.respond(ParameterResponse(examSeries, "Successfully located all subjects"))
    }

    post("/exam_series") {
        val examSeries = transaction { ExamQuestions.selectAll().map { it[ExamQuestions.examSeries] }.distinct() }
        call.respond(ParameterResponse(examSeries, "Successfully located all exam series"))
    }

    post("/papers") {
        val papers = transaction { ExamQuestions.selectAll().map { it[ExamQuestions.paper] }.distinct() }
        call.respond(ParameterResponse(papers, "Successfully located all papers"))
    }

    post("/tags") {
        val tags = transaction { ExamQuestions.selectAll().map { it[ExamQuestions.tags] }.flatten().distinct() }
        call.respond(ParameterResponse(tags, "Successfully located all tags"))
    }
}