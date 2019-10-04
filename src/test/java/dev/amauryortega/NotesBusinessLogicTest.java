package dev.amauryortega;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import com.github.javafaker.Faker;

import org.junit.jupiter.api.Test;

import dev.amauryortega.model.Note;
import dev.amauryortega.services.BusinessLogic;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class NotesBusinessLogicTest {

    @Inject
    private BusinessLogic service;

    @Test
    public void test_empty_array_of_notes() {
        assertIterableEquals(new ArrayList<Note>(), service.listNotes());
    }

    @Test
    public void test_array_of_2_notes() {
        Faker faker = new Faker();
        ArrayList<Note> inserted_notes = new ArrayList<Note>();
        for (int i = 0; i < 2; i++) {
            inserted_notes.add(service.createNote(
                faker.lorem().sentence(),
                faker.name().fullName(),
                faker.lorem().paragraph()
            ));
        }
        assertIterableEquals(inserted_notes, service.listNotes());
        for (Note note : service.listNotes()) {
            service.deleteNote(note.uuid);
        }
        assertIterableEquals(new ArrayList<Note>(), service.listNotes());
    }

    @Test
    public void test_creation_of_note() {
        Note note_to_insert = new Note("My unit title", "Unit test runner", "Unit test test_creation_of_note");
        Note inserted_note = service.createNote(note_to_insert.title, note_to_insert.author, note_to_insert.body);

        assertEquals(note_to_insert.author, inserted_note.author);
        assertEquals(note_to_insert.body, inserted_note.body);
        assertEquals(note_to_insert.title, inserted_note.title);
        UUID.fromString(inserted_note.uuid);

        service.deleteNote(inserted_note.uuid);
        assertIterableEquals(new ArrayList<Note>(), service.listNotes());
    }

    @Test
    public void test_delete_note() {
        Note note_to_insert = new Note("My unit title", "Unit test runner", "Unit test test_delete_note");
        Note inserted_note = service.createNote(note_to_insert.title, note_to_insert.author, note_to_insert.body);

        assertEquals(note_to_insert.author, inserted_note.author);
        assertEquals(note_to_insert.body, inserted_note.body);
        assertEquals(note_to_insert.title, inserted_note.title);
        UUID.fromString(inserted_note.uuid);

        service.deleteNote(inserted_note.uuid);
        assertIterableEquals(new ArrayList<Note>(), service.listNotes());
    }

    @Test
    public void test_get_inserted_note_after_inserting_5_notes() {
        Faker faker = new Faker();
        ArrayList<Note> inserted_notes = new ArrayList<Note>();
        for (int i = 0; i < 5; i++) {
            inserted_notes.add(service.createNote(
                faker.lorem().sentence(),
                faker.name().fullName(),
                faker.lorem().paragraph()
            ));
        }
        assertIterableEquals(inserted_notes, service.listNotes());

        Note last_note = inserted_notes.get(inserted_notes.size() - 1);
        assertEquals(last_note, service.getNote(last_note.uuid));

        for (Note note : service.listNotes()) {
            service.deleteNote(note.uuid);
        }
        assertIterableEquals(new ArrayList<Note>(), service.listNotes());
    }

}