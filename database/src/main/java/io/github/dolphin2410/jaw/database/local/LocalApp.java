package io.github.dolphin2410.jaw.database.local;

import io.github.dolphin2410.jaw.database.App;
import io.github.dolphin2410.jaw.reflection.ConstructorAccessor;
import io.github.dolphin2410.jaw.reflection.FieldAccessor;
import io.github.dolphin2410.jaw.util.io.LocalFile;
import io.github.dolphin2410.jaw.util.kotlin.KWrapper;
import java.io.File;

/**
 * A local database application class. Look in the source code for developer notes.
 *
 * @author dolphin2410
 */
public final class LocalApp implements App {
    private final LocalDatabase database = null;
    private final File file;
    private LocalApp(File file) {
        this.file = file;
    }
    private LocalDatabase _getDatabase() {
        if (database == null) {
            ConstructorAccessor<LocalDatabase> databaseConstructor = new ConstructorAccessor<>(LocalDatabase.class);
            return KWrapper.apply(databaseConstructor.newInstance(new LocalFile(this.file)), (newDatabase)->new FieldAccessor<>(this, "database").set(newDatabase));
        } else {
            return database;
        }
    }
    private static LocalApp initialize(File file) {
        ConstructorAccessor<LocalApp> appConstructor = new ConstructorAccessor<>(LocalApp.class);
        return appConstructor.newInstance(file);
    }
}
