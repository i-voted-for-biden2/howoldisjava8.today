FROM adoptopenjdk/openjdk15-openj9 as builder

COPY . .

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk15-openj9

WORKDIR /app

COPY --from=builder build/install/rest-api ./

ENTRYPOINT ["/app/bin/rest-api"]
