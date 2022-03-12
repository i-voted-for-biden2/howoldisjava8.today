FROM ibm-semeru-runtimes:open-17-jdk as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM ibm-semeru-runtimes:open-17-jre

WORKDIR /usr/app

COPY --from=builder build/install/twitter-bot ./

ENTRYPOINT ["/usr/app/bin/twitter-bot"]
