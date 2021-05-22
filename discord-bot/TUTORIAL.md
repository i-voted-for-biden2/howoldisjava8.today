# Sections
- [Kordx.commands](#kordxcommands)
- [Slash Commands](#slash-commands)
- [How to deploy the bot?](#how-to-deploy-the-bot)
- [Lavakord](#lavakord)

# Kordx.commands

The Bot uses Kord's own [Kordx.commands](https://github.com/kordlib/kordx.commands) command library.
```kotlin
@AutoWired
@ModuleName("General")
fun howOldIsJava8() = command("howoldisjava8") {
  // The invoke method is the one, as the name suggests, that gets called whenever a command gets invokes
  invoke(
    // You can either provide no arguments or up to 23 arguments like BooleanArgument
    BooleanArgument
      // Arguments can have names
      .named("use-tts")
      // And default values
      .withDefault(false)
  ) { useTTS -> // The arguments later translate
    // into lambda arguments, which are type and null-safe
    sendNotice(useTTS) // see core/AgeHandler.kt
  }
}
```
You can find the full example [here](https://github.com/i-voted-for-biden2/howoldisjava8.today/blob/main/discord-bot/src/main/kotlin/today/howoldisjava8/discordbot/commands/HowOldIsJava8.kt#L36-L68)

# Slash Commands

Kord also supports Discord's new Slash Commands as a Preview Feature, therefore Kordx.commands also does not support them yet

There are two types of Slash Commands. Guild Commands and Global Commands.
- Guild Commands: Work only on the Guild where created (requires application.commands scope to create)
- Global Commands: Works in DMs and on any Guild the bot has the application.commands scope

A Global command can be created like this:
```kotlin
kord.slashCommands.createGlobalApplicationCommand("howoldisjava8", "Tells you the age of Java 8") {
      boolean("use-tts", "Read the message using TTS") {
        default = false
      }
    }
```
This creates the command `/howoldisjava8` with a `use-tts` parameter of type boolean that defaults to `false`

As Global commands have an update time of ~ 2 hours for testing you might want to use Guild commands for testing as they update instantly. This can be done by using `createGuildApplicationCommand` instead

You can find the full example [here](https://github.com/DRSchlaubi/java8isancient/blob/main/discord-bot/src/main/kotlin/today/howoldisjava8/discord_bot/commands/HowOldIsJava8.kt#L36-L68)

# How to deploy the bot?
An easy way to deploy the bot is Docker.

Gradle has a plugin called `application` which allows you to generate distributions for your bot. A possible configuration could look like this:

```kotlin
plugins {
  application
}

application {
  mainClass.set("today.howoldisjava8.discord_bot.LauncherKt")
}
```

Then you can convert this into a Docker image using a Dockerfile like this

```Dockerfile
FROM adoptopenjdk/openjdk15-openj9 as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk15-openj9

WORKDIR /user/app

COPY --from=builder build/install/discord-bot ./

ENTRYPOINT ["/user/app/bin/discord-bot"]
```

An easy way of deploying said image is Heroku. An example on how this bot does this can be found [here](https://github.com/DRSchlaubi/java8isancient/blob/main/.github/workflows/discord-bot-cd.yml#L28-L34)
You can also find our Docker images [here](https://github.com/users/DRSchlaubi/packages/container/package/java8isancient%2Fbot)

# Lavakord
Due to a KTor bug Kord does currently not support voice (see [kordlib/kord#101](https://github.com/kordlib/kord/issues/101) for more info).
Luckily there is a workaround. You can use [Lavalink](https://github.com/Frederikam/Lavalink) and the [Lavalink.kt](https://github.com/DRSchlaubi/Lavalink.kt/tree/feature/mpp) wrapper
