@file:OptIn(KtorExperimentalLocationsAPI::class)

package today.howoldisjava8.api.v1

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import today.howoldisjava8.api.core.AgeService
import today.howoldisjava8.api.v1.Format.MESSAGE
import today.howoldisjava8.api.v1.Format.PERIOD

@Location("/")
data class RootRoute(val format: Format = MESSAGE)

enum class Format {
  MESSAGE,
  PERIOD
}

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.apiV1() = route("v1") {
  val ageService: AgeService by inject()
  get<RootRoute> {
    when (it.format) {
      MESSAGE -> {
        val message = ageService.getAsMessage()
        call.respond(mapOf("message" to message))
      }
      PERIOD -> {
        val period = ageService.getAsPeriod()
        call.respond(period)
      }
    }
  }
}
