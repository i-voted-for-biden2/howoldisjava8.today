@file:OptIn(KtorExperimentalLocationsAPI::class)

package today.howoldisjava8.api.v1

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import today.howoldisjava8.api.core.AgeService
import today.howoldisjava8.api.core.InvalidApiFormatException

@Location("/")
data class RootRoute(val format: String = "message")

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.apiV1() = route("v1") {
  val ageService: AgeService by inject()
  get<RootRoute> {
    if (it.format !in arrayOf("message", "period")) {
      throw InvalidApiFormatException(it.format)
    }

    when (it.format) {
      "message" -> {
        val message = ageService.getAsMessage()
        call.respond(mapOf("message" to message))
      }
      "period" -> {
        val period = ageService.getAsPeriod()
        call.respond(period)
      }
    }
  }
}
