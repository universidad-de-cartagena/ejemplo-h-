package dev.amauryortega.controllers;

import java.util.ArrayList;

import javax.inject.Inject;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dev.amauryortega.model.Note;
import dev.amauryortega.model.NoteAdapter;
import dev.amauryortega.services.BusinessLogic;;

// Path can have a slash at the start but it is ignored by design
// https://javaee.github.io/javaee-spec/javadocs/javax/ws/rs/Path.html
@Path("notes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NoteController {
    @Inject
    protected BusinessLogic businessService;
    private JsonbConfig config;
    private Jsonb jsonb;

    public NoteController() {
        this.config = new JsonbConfig().withAdapters(new NoteAdapter());
        this.jsonb = JsonbBuilder.create(config);
    }

    @GET
    public Response list() {
        ArrayList<Note> result = businessService.listNotes();
        return Response.status(Status.OK).entity(jsonb.toJson(result)).build();
    }

    @POST
    public Response insert(String request) {
        Note note_to_insert = jsonb.fromJson(request, Note.class);
        Note created_note = businessService.createNote(note_to_insert.title, note_to_insert.author,
                note_to_insert.body);
        return Response.status(Status.CREATED).entity(jsonb.toJson(created_note)).build();
    }

    @DELETE
    @Path("{uuid}")
    public Response delete(@PathParam("uuid") String uuid) {
        businessService.deleteNote(uuid);
        String message = "{\"message\": \"" + uuid + " Deleted\"}";
        return Response.status(Status.OK).entity(message).build();
    }

    @GET
    @Path("{uuid}")
    public Response get(@PathParam("uuid") String uuid) {
        return Response.status(Status.OK).entity(jsonb.toJson(businessService.getNote(uuid))).build();
    }
}
