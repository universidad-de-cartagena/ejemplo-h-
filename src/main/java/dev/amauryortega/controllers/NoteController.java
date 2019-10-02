package dev.amauryortega.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dev.amauryortega.model.Note;
import dev.amauryortega.model.NoteAdapter;

// Path can have a slash at the start but it is ignored by design
// https://javaee.github.io/javaee-spec/javadocs/javax/ws/rs/Path.html
@Path("notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteController {
    private List<Note> db = new LinkedList<Note>();
    private JsonbConfig config;
    private Jsonb jsonb;

    public NoteController() {
        this.config = new JsonbConfig().withAdapters(new NoteAdapter());
        this.jsonb = JsonbBuilder.create(config);
    }

    @GET
    public String list() {
        return jsonb.toJson(db);
    }

    @POST
    public String insert(String request) {
        Note note_to_insert = jsonb.fromJson(request, Note.class);
        db.add(note_to_insert);
        return jsonb.toJson(db.get(db.indexOf(note_to_insert)));
    }

    @DELETE
    @Path("{uuid}")
    public String delete(@PathParam("uuid") String uuid) {
        db.remove(db.get(db.size() - 1));
        return "{\"message\": \"" + uuid + " Deleted\"}";
    }

    @GET
    @Path("{uuid}")
    public String get(@PathParam("uuid") String uuid) {
        db.get(db.size() - 1);
        return "{\"message\": \"" + uuid + " obtained\"}";
    }
}
