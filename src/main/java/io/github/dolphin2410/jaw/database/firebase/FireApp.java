package io.github.dolphin2410.jaw.database.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.github.dolphin2410.jaw.database.App;
import io.github.dolphin2410.jaw.reflection.ConstructorAccessor;
import io.github.dolphin2410.jaw.reflection.FieldAccessor;
import io.github.dolphin2410.jaw.util.kotlin.KWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * A firebase database application class. Look in the source code for developer notes.
 *
 * @author dolphin2410
 */
public final class FireApp implements App {
    private final FireDatabase database = null;
    private final FirebaseApp app = null;
    private FireApp() {}
    private static FireApp initialize(File path, URL databaseUrl) {
        // For developers: This method is called with reflection in App#initializeFirebase
        // For developers: This typically does Firebase-Admin-Sdk registration and initializing the instance.
        // For developers: Don't be confused. FireApp is the class I wrote and FirebaseApp is the class in the firebase admin sdk.
        return KWrapper.apply(new ConstructorAccessor<>(FireApp.class).newInstance(), newApp -> {
            try {
                new FieldAccessor<>(newApp, "app").set(FirebaseApp.initializeApp(
                        FirebaseOptions.builder().setCredentials(
                                GoogleCredentials.fromStream(new FileInputStream(path)))
                                .setDatabaseUrl(databaseUrl.toString())
                                .build()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private FireDatabase _getDatabase() {
        // For developers: This process determines whether you need to create a new instance or get the previous one.
        if (database == null) {
            ConstructorAccessor<FireDatabase> fireDatabaseConstructor = new ConstructorAccessor<>(FireDatabase.class);
            return KWrapper.apply(fireDatabaseConstructor.newInstance(this), (newDatabase)->new FieldAccessor<>(this, "database").set(newDatabase));
        } else {
            return database;
        }
    }
}
