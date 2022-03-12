package today.howoldisjava8.twitterbot.twitter

import io.github.redouane59.twitter.signature.TwitterCredentials

inline fun credentials(builder: TwitterCredentials.() -> Unit): TwitterCredentials {
    return TwitterCredentials().apply(builder)
}
