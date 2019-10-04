package dev.amauryortega.model;

import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class NoteAdapter implements JsonbAdapter<Note, JsonObject> {

    @Override
    public JsonObject adaptToJson(Note obj) throws Exception {
        return Json.createObjectBuilder()
            .add("author", obj.author)
            .add("body", obj.body)
            .add("created_at", new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss:S'Z'").format(obj.created_at))
            .add("title", obj.title)
            .add("uuid", obj.uuid)
            .build();
    }

    @Override
    public Note adaptFromJson(JsonObject obj) throws Exception {
        return new Note(
            obj.getString("title"),
            obj.getString("author"),
            obj.getString("body")
        );
    }

}
