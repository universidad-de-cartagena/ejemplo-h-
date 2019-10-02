package dev.amauryortega.services;

import java.util.ArrayList;

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
        this.entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Note> query = this.entityManager.createQuery("SELECT c FROM Note c", Note.class);
        ArrayList<Note> notes = new ArrayList<Note>(query.getResultList());
        this.entityManager.close();

        return notes;
    }

    @Transactional
    public Note createNote(String title, String author, String body) {
        Note new_note = new Note(title, author, body);

        this.entityManager = entityManagerFactory.createEntityManager();
        this.entityManager.persist(new_note);
        this.entityManager.flush();
        Note found_note = this.entityManager.find(Note.class, new_note.id);
        this.entityManager.close();

        return found_note;
    }
}