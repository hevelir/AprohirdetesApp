package com.example.mobwebhazi.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

    @Database(
            entities = {Item.class},
            version = 1
    )
    @TypeConverters(value = {Item.Category.class})
    public abstract class ItemListDatabase extends RoomDatabase {
        public abstract ItemDao ItemDao();
    }

