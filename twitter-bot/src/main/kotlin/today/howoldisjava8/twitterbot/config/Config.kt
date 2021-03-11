package today.howoldisjava8.twitterbot.config

object Config {

  val TWITTER_BEARER_TOKEN: String by getEnv()
  val TWITTER_API_KEY: String by getEnv()
  val TWITTER_API_SECRET: String by getEnv()
  val TWITTER_ACCESS_TOKEN: String by getEnv()
  val TWITTER_ACCESS_SECRET: String by getEnv()

}
