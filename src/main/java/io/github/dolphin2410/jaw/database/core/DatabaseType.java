package io.github.dolphin2410.jaw.database.core;

import io.github.dolphin2410.jaw.database.firebase.FireDatabase;
import io.github.dolphin2410.jaw.database.local.LocalDatabase;

/**
 * Database Types
 *
 * @author dolphin2410
 */
public enum DatabaseType {
    LOCAL(LocalDatabase.class),
    FIREBASE(FireDatabase.class);
    public final Class<? extends Database> clazz;
    DatabaseType(Class<? extends Database> clazz) {
        this.clazz = clazz;
    }
}
