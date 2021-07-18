package io.github.dolphin2410.jaw.database.local;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import io.github.dolphin2410.jaw.database.core.Database;
import io.github.dolphin2410.jaw.database.core.DatabaseType;
import io.github.dolphin2410.jaw.util.async.Async;
import io.github.dolphin2410.jaw.util.collection.IndexedList;
import io.github.dolphin2410.jaw.util.collection.Pair;
import io.github.dolphin2410.jaw.util.core.Nothing;
import io.github.dolphin2410.jaw.util.io.LocalFile;
import io.github.dolphin2410.jaw.util.kotlin.KWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * A local database class. Look at the source code for developer notes.
 *
 * @author dolphin2410
 */
public final class LocalDatabase implements Database {
    private final LocalFile file;
    private LocalDatabase(LocalFile file) {
        this.file = file;
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.LOCAL;
    }

    @Override
    public void validateDatabase() {
        try {
            JsonParser.parseString(file.readAllContents());
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        }
    }
    private CompletableFuture<JsonObject> _readAsync() {
        validateDatabase();
        CompletableFuture<JsonObject> future = new CompletableFuture<>();
        Async.execute(()-> future.complete(JsonParser.parseString(file.readAllContents().equals("") ? KWrapper.apply("{}", file::writeContent) : file.readAllContents()).getAsJsonObject()));
        return future;
    }
    private CompletableFuture<Nothing> _writeAsync(String key, String value) {
        validateDatabase();
        if (!key.matches("^[a-zA-Z\\d]*([/.-][a-zA-Z\\d]+)*$")) {
            throw new RuntimeException("The key doesn't match the regex pattern. \nUse a dot, forward slash or hyphen to distinguish sub references");
        }
        read();
        CompletableFuture<Nothing> future = new CompletableFuture<>();
        Async.execute(()->{
            JsonObject databaseContent = JsonParser.parseString(file.readAllContents()).getAsJsonObject();
            JsonObject currentObject = databaseContent;
            IndexedList<String> subKeys = IndexedList.of(key.replace(".", "/").replace("-", "/").split("/"));
            for (Pair<Integer, String> subKey : subKeys) {
                // If not in the last loop
                if (subKey.getFirst() != subKeys.size() - 1) {
                    // Not the last. Doesn't handle string. Only create objects
                        if (currentObject.has(subKey.getSecond())) {
                            currentObject.remove(subKey.getSecond());
                        }
                    JsonObject finalCurrentObject = currentObject;
                    currentObject = KWrapper.apply(new JsonObject(), it -> finalCurrentObject.add(subKey.getSecond(), it));
                } else {
                    // The last loop execution
                    currentObject.getAsJsonObject().remove(subKey.getSecond());
                    currentObject.getAsJsonObject().addProperty(subKey.getSecond(), value);
                    future.complete(Nothing.INSTANCE);
                }
            }
            file.writeContent(databaseContent.toString());
            future.complete(Nothing.INSTANCE);
        });
        return future;
    }
}
