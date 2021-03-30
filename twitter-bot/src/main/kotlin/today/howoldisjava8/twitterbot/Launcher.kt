package today.howoldisjava8.twitterbot

import com.github.redouane59.twitter.TwitterClient
import com.github.redouane59.twitter.dto.stream.StreamRules
import dev.inmo.krontab.doWhile
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import today.howoldisjava8.twitterbot.config.Config
import today.howoldisjava8.twitterbot.core.formatMessage
import today.howoldisjava8.twitterbot.twitter.credentials
import java.util.*
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Future
import kotlin.coroutines.resumeWithException

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun main() {
  TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"))
  val twitter = TwitterClient(credentials {
    apiKey = Config.TWITTER_API_KEY
    apiSecretKey = Config.TWITTER_API_SECRET
    accessToken = Config.TWITTER_ACCESS_TOKEN
    accessTokenSecret = Config.TWITTER_ACCESS_SECRET
  })

  val rules: List<StreamRules.StreamRule>? = twitter.retrieveFilteredStreamRules()
  println("Found ${rules?.count() ?: 0} rules!")

  val client = HttpClient(OkHttp) {
    install(JsonFeature) {
      serializer = KotlinxSerializer()
    }
  }

  if (rules?.any { it.tag == "java" } == true) {

    client.post<HttpResponse>("https://api.twitter.com/2/tweets/search/stream/rules") {
      header(HttpHeaders.Authorization, "Bearer ${Config.TWITTER_BEARER_TOKEN}")
      header(HttpHeaders.ContentType, ContentType.Application.Json)
      body = buildJsonObject {
        putJsonObject("delete") {
          putJsonArray("ids") {
            rules.forEach { add(it.id) }
          }
        }
      }
    }
  }

  GlobalScope.launch {
    doWhile("0 10 * * *") {
      twitter.postTweet(formatMessage())
      true
    }
  }

  twitter.addFilteredStreamRule("(how java old 8)", "java")

  println("Starting Tweet Stream")
  twitter.startFilteredStream {
    GlobalScope.launch {
      val user = twitter.getUserFromUserId(it.authorId).name
      if (user == "HowOldIsJava8")
        return@launch
      println("${it.text} by $user")
      twitter.postTweet("@$user, ${formatMessage()}", it.id)
    }
  }.await()
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun <T> Future<T>.await(): T = suspendCancellableCoroutine { cont ->
  ForkJoinPool.managedBlock(object : ForkJoinPool.ManagedBlocker {
    override fun block(): Boolean {
      try {
        cont.resume(get()) { cont.cancel(it) }
      } catch (e: Throwable) {
        cont.resumeWithException(e)
        return false
      }

      return true
    }

    override fun isReleasable(): Boolean = isDone
  })
}
