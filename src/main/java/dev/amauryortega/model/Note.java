package dev.amauryortega.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull
    @Column(unique = true)
    public UUID uuid;
    @NotNull
    public String title;
    @NotNull
    public String author;
    @NotNull
    public String body;
    @NotNull
    public Date created_at;

    public Note(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.created_at = new Date();
        this.uuid = UUID.randomUUID();
    }

    public Note() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Note)) {
            return false;
        }
        Note other = (Note) obj;
        return Objects.equals(other.uuid, this.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid);
    }
}