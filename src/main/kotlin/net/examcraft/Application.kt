package net.examcraft

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import net.examcraft.plugins.configureDatabases
import net.examcraft.plugins.configureMonitoring
import net.examcraft.plugins.configureRouting
import net.examcraft.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
