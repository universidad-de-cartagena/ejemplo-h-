package dev.amauryortega.services;

import java.util.ArrayList;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import dev.amauryortega.model.Note;

@ApplicationScoped
public class BusinessLogic {
    @Inject
    protected EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Transactional
    public ArrayList<Note> listNotes() {
        // Database operations
        this.entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Note> query = this.entityManager.createQuery("SELECT c FROM Note c", Note.class);
        ArrayList<Note> notes = new ArrayList<Note>(query.getResultList());
        this.entityManager.close();

        return notes;
    }

    @Transactional
    public Note createNote(String title, String author, String body) {
        Note new_note = new Note(title, author, body);

        // Database operations
        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityManager.persist(new_note);
        this.entityManager.flush();
        Note found_note = this.entityManager.find(Note.class, new_note.id);
        this.entityManager.close();

        return found_note;
    }

    @Transactional
    public Note getNote(String uuid) {
        // getNote could not find a note with uuid: ABCD

        UUID.fromString(uuid);

        // Database operations
        this.entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Note> query = this.entityManager.createQuery("SELECT c FROM Note c WHERE c.uuid = '" + uuid + "'", Note.class);
        Note found_note = query.getSingleResult();
        this.entityManager.close();

        return found_note;
    }

    @Transactional
    public void deleteNote(String uuid) {
        // deleteNote could not delete the note because getNote could not find a note with uuid: ABCD

        UUID.fromString(uuid);

        // Database operations
        this.entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Note> query = this.entityManager.createQuery("SELECT c FROM Note c WHERE c.uuid = '" + uuid + "'", Note.class);
        Note found_note = query.getSingleResult();
        this.entityManager.remove(found_note);
        this.entityManager.close();
    }
}