package dev.amauryortega;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class NotesIntegrationTest {

    @Test
    public void testHelloEndpoint() {
        given().when().get("/hello").then().statusCode(200).body(is("hello PorDefecto"));
    }

    @Test
    public void test_empty_array_of_notes() {
        given().when().get("/notes").then().statusCode(200).body(is("[]"));
    }

    @Test
    public void test_insert_note() {

    }

    @Test
    public void test_get_inserted_note() {

    }

    @Test
    public void test_delete_note() {

    }

    @Test
    public void test_delete_non_existent_note() {

    }

    @Test
    public void test_delete_endpoint_without_uuid() {

    }

    @Test
    public void test_get_endpoint_with_non_existent_uuid() {

    }

    @Test
    public void test_non_supported_http_method() {

    }
}