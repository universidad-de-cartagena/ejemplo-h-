package dev.amauryortega.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Note {
    protected UUID uuid;
    protected String title;
    protected String author;
    protected String body;
    protected Date created_at;

    public Note(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.created_at = new Date();
        this.uuid = UUID.randomUUID();
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