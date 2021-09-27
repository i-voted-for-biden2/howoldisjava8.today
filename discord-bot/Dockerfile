FROM adoptopenjdk/openjdk16:alpine as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk16:alpine-jre

WORKDIR /user/app

COPY --from=builder build/install/discord-bot ./

ENTRYPOINT ["/user/app/bin/discord-bot"]
