package today.howoldisjava8.twitterbot.config

import dev.schlaubi.envconf.environment

object Config {

    val TWITTER_BEARER_TOKEN: String by environment
    val TWITTER_API_KEY: String by environment
    val TWITTER_API_SECRET: String by environment
    val TWITTER_ACCESS_TOKEN: String by environment
    val TWITTER_ACCESS_SECRET: String by environment

}
