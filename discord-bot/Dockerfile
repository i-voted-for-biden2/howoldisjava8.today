FROM adoptopenjdk/openjdk16-openj9:jre as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk15-openj9

WORKDIR /user/app

COPY --from=builder build/install/discord-bot ./

ENTRYPOINT ["/user/app/bin/discord-bot"]
