FROM openjdk:23-jdk-bookworm AS build
WORKDIR /app
COPY /app .
RUN ./gradlew installDist --no-daemon --warning-mode all


FROM openjdk:23-jdk-bookworm AS runner
COPY --from=build /app/build/install/app /app
CMD ["/app/bin/app"]
