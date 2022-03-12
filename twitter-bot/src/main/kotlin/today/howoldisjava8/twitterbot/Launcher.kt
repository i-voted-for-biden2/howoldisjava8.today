package today.howoldisjava8.twitterbot

import dev.inmo.krontab.doWhile
import io.github.redouane59.twitter.TwitterClient
import io.github.redouane59.twitter.dto.stream.StreamRules
import io.github.redouane59.twitter.dto.tweet.TweetParameters
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import today.howoldisjava8.twitterbot.config.Config
import today.howoldisjava8.twitterbot.core.formatMessage
import today.howoldisjava8.twitterbot.twitter.credentials
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Future
import kotlin.coroutines.resumeWithException

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun main() {
    val logger = LoggerFactory.getLogger("TwitterBot")
    val twitter = TwitterClient(credentials {
        apiKey = Config.TWITTER_API_KEY
        apiSecretKey = Config.TWITTER_API_SECRET
        accessToken = Config.TWITTER_ACCESS_TOKEN
        accessTokenSecret = Config.TWITTER_ACCESS_SECRET
    })

    val rules: List<StreamRules.StreamRule>? = twitter.retrieveFilteredStreamRules()
    logger.info("Found ${rules?.count() ?: 0} rules!")

    val scope = CoroutineScope(Dispatchers.IO) + SupervisorJob()

    if (rules?.any { it.tag == "java" } == true) {
        rules.forEach { rule ->
            scope.launch {
                twitter.deleteFilteredStreamRuleId(rule.id)
            }
        }
    }

    scope.launch {
        doWhile("0 0 10 * * *") {
            twitter.postTweet(formatMessage())
            true
        }
    }

    twitter.addFilteredStreamRule("(how java old 8)", "java")

    logger.info("Starting Tweet Stream")
    twitter.startFilteredStream {
        val user = twitter.getUserFromUserId(it.authorId).name
        if (user == twitter.userIdFromAccessToken)
            return@startFilteredStream
        logger.info("${it.text} by $user")
        twitter.postTweet(TweetParameters.builder()
            .reply(TweetParameters.Reply.builder()
                .inReplyToTweetId(it.id)
                .build())
            .text(formatMessage()).build())
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
