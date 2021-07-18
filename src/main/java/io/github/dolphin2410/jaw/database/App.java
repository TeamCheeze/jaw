package io.github.dolphin2410.jaw.database;

import io.github.dolphin2410.jaw.database.core.Database;
import io.github.dolphin2410.jaw.database.firebase.FireApp;
import io.github.dolphin2410.jaw.database.local.LocalApp;
import io.github.dolphin2410.jaw.reflection.MethodAccessor;
import io.github.dolphin2410.jaw.reflection.ReflectionException;

import java.io.File;
import java.net.URL;

/**
 * The application interface. Use this class to initialize your app.
 *
 * @author dolphin2410
 */
public interface App {
    static FireApp initializeFirebase(File credentials, URL dbURL) {
        return (FireApp) new MethodAccessor<>(FireApp.class, "initialize").invoke(credentials, dbURL);
    }
    static LocalApp initializeLocal(File databaseFile) {
        try {
            return (LocalApp) new MethodAccessor<>(LocalApp.class, "initialize").invoke(databaseFile);
        } catch (ReflectionException e) {
            throw e.raw;
        }
    }
    default Database getDatabase() {
        return (Database) new MethodAccessor<>(this, "_getDatabase").invoke();
    }
}
