package io.github.dolphin2410.jaw.database.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.gson.JsonObject;
import io.github.dolphin2410.jaw.database.core.Database;
import io.github.dolphin2410.jaw.database.core.DatabaseType;
import io.github.dolphin2410.jaw.reflection.FieldAccessor;
import io.github.dolphin2410.jaw.util.core.Nothing;
import io.github.dolphin2410.jaw.util.gson.JsonParserWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * A firebase database class. Look at the source code for developer notes.
 *
 * @author dolphin2410
 */
public final class FireDatabase implements Database {
    private final FirebaseDatabase fbDb;
    private FireDatabase(FireApp app) {
        this.fbDb = FirebaseDatabase.getInstance((FirebaseApp) new FieldAccessor<>(app, "app").get());
    }
    @Override
    public void validateDatabase() {}

    @Override
    public DatabaseType getType() {
        return DatabaseType.FIREBASE;
    }
    public CompletableFuture<Nothing> _writeAsync(String key, String value) {
        validateDatabase();
        if (this.fbDb == null) {
            throw new RuntimeException("");
        }
        CompletableFuture<Nothing> future = new CompletableFuture<>();
        // For developers: This is a regex pattern that matches any strings or numbers that is separated with either / . or -
        if (!key.matches("^[a-zA-Z\\d]*([/.-][a-zA-Z\\d]+)*$")) {
            throw new RuntimeException("The key doesn't match the regex pattern. \nUse a dot, forward slash or hyphen to distinguish sub references");
        }
        // For developers: This gets a firebase reference for the key
        DatabaseReference ref = this.fbDb.getReference(key.replace("-", "/").replace(".", "/"));

        // For developers: This sets a value asynchronously
        ref.setValue(value, (error, reference) -> {
            if (error == null) {
                // For developers: When there is no error, complete the future
                future.complete(Nothing.INSTANCE);
            } else {
                // For developers: This will throw a new DatabaseException based on the error message
                throw new RuntimeException(error.toException());
            }
        });
        return future;
    }
    private CompletableFuture<JsonObject> _readAsync() {
        validateDatabase();
        if (this.fbDb == null) {
            throw new RuntimeException("");
        }
        DatabaseReference ref = this.fbDb.getReference("");
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                future.complete(JsonParserWrapper.parseString(snapshot.getValue().toString()).getAsJsonObject());
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        return future;
    }
}
