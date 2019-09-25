# Ejemplo java

## Documentacion clave

[Quarkus](https://quarkus.io/guides/)

## Extensiones usadas

[OpenAPI + Swagger](https://quarkus.io/guides/openapi-swaggerui-guide)

## CI

./mvnw --batch-mode (non-interactive)

ObjectStore folder is created when running tests. Usually from `mvn package`. More info: [ObjectStore](https://docs.jboss.org/jbosstm/docs/4.2/javadoc/jts/com/arjuna/ats/arjuna/objectstore/package-summary.html) or [ObjectStore](https://en.wikipedia.org/wiki/ObjectStore). If this provides access issues in docker, follow this [issue](https://github.com/quarkusio/quarkus/issues/2702)

By default maven-surefire-plugin creates test reports at `target/surefire-reports/*.xml`. To create coverage reports look into [JaCoCo](https://quarkus.io/guides/tests-with-coverage-guide#measuring-the-coverage-of-junit-tests-using-jacoco).

To create a fat here look [here with native](https://www.baeldung.com/quarkus-io) and without native look [here](https://quarkus.io/guides/maven-tooling#uber-jar-maven).
