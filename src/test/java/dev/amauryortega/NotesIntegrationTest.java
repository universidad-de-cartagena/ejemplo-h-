package dev.amauryortega;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dev.amauryortega.model.Note;
import dev.amauryortega.model.NoteAdapter;
import dev.amauryortega.services.BusinessLogic;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class NotesIntegrationTest {
    @Inject
    private BusinessLogic service;
    private static Faker faker;
    private static JsonbConfig config;
    private static Jsonb jsonb;

    @BeforeAll
    private static void initializeProperties() {
        faker = new Faker();
        config = new JsonbConfig().withAdapters(new NoteAdapter());
        jsonb = JsonbBuilder.create(config);
    }

    @Test
    public void test_empty_array_of_notes() {
        given().when().get("/notes").then().statusCode(200).body(is("[]"));
    }

    @Test
    public void test_insert_note() {
        Note note_to_insert = new Note("test_insert_note", "integration runner", faker.lorem().paragraph());
        given().
            contentType("application/json").
            body(jsonb.toJson(note_to_insert)).
        when().
            post("/notes/").
        then().assertThat().
            statusCode(201).
            body("title", equalTo(note_to_insert.title)).
            body("author", equalTo(note_to_insert.author)).
            body("body", equalTo(note_to_insert.body));
        service.deleteNote(service.listNotes().get(0).uuid);
    }

    @Test
    public void test_get_inserted_note() {
        Note inserted_note = service.createNote("test_insert_note", "integration runner", faker.lorem().paragraph());        
        given().
            contentType("application/json").
        when().
            get("/notes/" + inserted_note.uuid).
        then().assertThat().
            statusCode(200).
            body("title", equalTo(inserted_note.title)).
            body("author", equalTo(inserted_note.author)).
            body("body", equalTo(inserted_note.body)).
            body("uuid", equalTo(inserted_note.uuid));
        service.deleteNote(inserted_note.uuid);
    }

    @Test
    public void test_delete_note() {
        Note inserted_note = service.createNote("test_insert_note", "integration runner", faker.lorem().paragraph());        
        given().
            contentType("application/json").
        when().
            delete("/notes/" + inserted_note.uuid).
        then().assertThat().
            statusCode(200).
            body("message", equalTo("Note with UUID: " + inserted_note.uuid + " has been deleted"));
        assertEquals(0, service.listNotes().size());
    }

    @Test
    public void test_delete_non_existent_note() {
        String random_uuid = UUID.randomUUID().toString();
        given().
            contentType("application/json").
        when().
            delete("/notes/" + random_uuid).
        then().assertThat().
            statusCode(404).
            body("message", equalTo("No note was found with UUID: " + random_uuid));
    }

    @Test
    public void test_delete_endpoint_without_uuid() {
        given().
            contentType("application/json").
        when().
            delete("/notes").
        then().assertThat().
            statusCode(400).
            body("message", equalTo("Provide the UUID of the note that wants to be deleted"));
    }

    @Test
    public void test_get_endpoint_with_non_existent_uuid() {
        String random_uuid = UUID.randomUUID().toString();
        given().
            contentType("application/json").
        when().
            get("/notes/" + random_uuid).
        then().assertThat().
            statusCode(404).
            body("message", equalTo("No note was found with UUID: " + random_uuid));
    }

    @Test
    public void test_non_supported_http_method() {
        given().
            contentType("application/json").
        when().
            patch("/notes").
        then().assertThat().
            statusCode(415).
            body("message", equalTo("Use one of the accepted HTTP methods ['GET', 'POST', 'DELETE']"));
    }
}