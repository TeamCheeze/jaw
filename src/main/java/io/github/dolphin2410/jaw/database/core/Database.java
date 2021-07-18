package io.github.dolphin2410.jaw.database.core;

import com.google.gson.JsonObject;
import io.github.dolphin2410.jaw.reflection.MethodAccessor;
import io.github.dolphin2410.jaw.util.core.Nothing;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The database interface
 *
 * @author dolphin2410
 */
public interface Database {
    /**
     * Gets database type
     * @return database type
     */
    DatabaseType getType();

    /**
     * A method that checks if the database is currently valid. This usually throws exception on error.
     */
    void validateDatabase();

    /**
     * The asynchronous method of reading the database value.
     * @return Whole data of the database
     */
    default CompletableFuture<JsonObject> readAsync() {
        @SuppressWarnings("unchecked")
        CompletableFuture<JsonObject> toReturn = (CompletableFuture<JsonObject>) new MethodAccessor<>(this, "_readAsync").invoke();
        return toReturn;
    }

    /**
     * The asynchronous method of writing to the database
     * @param key The key to write
     * @param value The value to write
     * @return Completable future that contains no information. This is used to notify you when the future is completed
     */
    default CompletableFuture<Nothing> writeAsync(String key, String value) {
        @SuppressWarnings("unchecked")
        CompletableFuture<Nothing> toReturn = (CompletableFuture<Nothing>) new MethodAccessor<>(this, "_writeAsync").invoke(key, value);
        return toReturn;
    }

    /**
     * A synchronous method of reading the database.
     * @return The whole data of the database
     */
    default JsonObject read() {
        validateDatabase();
        try {
            return readAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A synchronous method of writing to the database.
     * @param key The key to write
     * @param value The value to write
     */
    default void write(String key, String value) {
        try {
            writeAsync(key, value).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
