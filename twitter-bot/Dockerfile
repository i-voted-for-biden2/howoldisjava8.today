FROM adoptopenjdk/openjdk16-openj9 as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk16-openj9:jre

WORKDIR /user/app

COPY --from=builder build/install/twitter-bot ./

ENTRYPOINT ["/user/app/bin/twitter-bot"]
