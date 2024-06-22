package net.examcraft.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.examcraft.routes.v1.exam.examTagsV1
import net.examcraft.routes.v1.exam.generateExamV1
import net.examcraft.routes.v1.responses.FailResponse
import net.examcraft.routes.v1.responses.SuccessResponse

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                FailResponse(cause.message ?: "An internal error occurred")
            )
        }
    }

    install(DoubleReceive)
    routing {
        get("/") {
            call.respond(SuccessResponse("Hello, World!"))
        }

        route("/v1") {
            route("/exam") {
                generateExamV1()
                examTagsV1()
            }
        }
    }
}
