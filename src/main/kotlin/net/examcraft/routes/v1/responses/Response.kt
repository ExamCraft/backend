package net.examcraft.routes.v1.responses

open class Response(val success: Boolean, val message: String?)

open class SuccessResponse(message: String? = null) : Response(true, message)

class FailResponse(message: String? = null) : Response(false, message)

class ParameterResponse(val data: Any, message: String? = null) : SuccessResponse(message)