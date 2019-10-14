# Ejemplo java

## Documentación clave

[Quarkus](https://quarkus.io/guides/)

## Extensiones usadas

[OpenAPI + Swagger](https://quarkus.io/guides/openapi-swaggerui-guide)

## CI

./mvnw --batch-mode (non-interactive)

ObjectStore folder is created when running tests. Usually from `mvn package`. More info: [ObjectStore](https://docs.jboss.org/jbosstm/docs/4.2/javadoc/jts/com/arjuna/ats/arjuna/objectstore/package-summary.html) or [ObjectStore](https://en.wikipedia.org/wiki/ObjectStore). If this provides access issues in docker, follow this [issue](https://github.com/quarkusio/quarkus/issues/2702)

By default maven-surefire-plugin creates test reports at `target/surefire-reports/*.xml`. To create coverage reports look into [JaCoCo](https://quarkus.io/guides/tests-with-coverage-guide#measuring-the-coverage-of-junit-tests-using-jacoco).

To create a fat here look [here with native](https://www.baeldung.com/quarkus-io) and without native look [here](https://quarkus.io/guides/maven-tooling#uber-jar-maven).

## Docker

```shell
java -Duser.timezone=UTC fat.jar
```

### Env

QUARKUS_HTTP_PORT=9090
QUARKUS_PROFILE=cloud

## Draft

[Adapt non-json like objects to json with JSONB using adapters](https://javaee.github.io/jsonb-spec/docs/user-guide.html#adapters)

[Environment variables with quarkus](https://lordofthejars.github.io/quarkus-cheat-sheet/). It works because of [microprofile](https://github.com/eclipse/microprofile-config).

[HTML report](https://maven.apache.org/surefire/maven-surefire-report-plugin/usage.html)

[Quarkus cheat sheet](https://lordofthejars.github.io/quarkus-cheat-sheet/)

See test reports on 8080

```shell
docker run --rm -it --init -v $PWD/target/surefire-reports/:/app:ro -w /app -p 8080:8080 node:10.16.3-alpine sh -c "npm install -g xunit-viewer && xunit-viewer --watch --results=/app/ --port=8080"
```

## Clean repo

```shell
git clean -dx -e .classpath -e .project -e .settings/ -e .vscode/ -n
git clean -dx -e .classpath -e .project -e .settings/ -e .vscode/ -f
```
