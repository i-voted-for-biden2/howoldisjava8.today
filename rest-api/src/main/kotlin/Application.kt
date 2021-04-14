package today.howoldisjava8.api

import guru.zoroark.ratelimit.RateLimit
import guru.zoroark.ratelimit.rateLimited
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.datetime.serializers.DateTimePeriodComponentSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger
import today.howoldisjava8.api.core.AgeService
import today.howoldisjava8.api.core.AgeServiceImpl
import today.howoldisjava8.api.core.InvalidApiFormatException
import today.howoldisjava8.api.v1.Format
import today.howoldisjava8.api.v1.apiV1
import kotlin.time.ExperimentalTime
import kotlin.time.minutes
import kotlin.time.toJavaDuration

val apiModule = module {
  single<AgeService> { AgeServiceImpl() }
}

@OptIn(ExperimentalTime::class)
fun Application.module(testing: Boolean = false) {
  install(CallLogging)
  install(DefaultHeaders)
  install(ContentNegotiation) {
    json(Json {
      prettyPrint = true
      serializersModule = SerializersModule {
        contextual(DateTimePeriodComponentSerializer)
      }
    })
  }
  install(CORS) {
    anyHost()
  }
  install(RateLimit) {
    limit = 60
    timeBeforeReset = 1.minutes.toJavaDuration()
  }
  install(Locations)
  install(Koin) {
    SLF4JLogger()
    modules(apiModule)
  }
  install(DataConversion) {
    convert(Format::class) {
      encode { if (it == null) emptyList() else listOf((it as Format).name.toLowerCase()) }
      decode { values, type -> Format.values().first { it.name.toLowerCase() in values } }
    }
  }
  install(StatusPages) {
    exception<InvalidApiFormatException> {
      call.respond(HttpStatusCode.BadRequest)
    }
  }
  routing {
    rateLimited {
      apiV1()
    }
  }
}
