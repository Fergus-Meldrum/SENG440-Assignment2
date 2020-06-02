package com.example.getuplord

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Photo class
@Database(entities = arrayOf(Photo::class), version = 1, exportSchema = false)
public abstract class PhotoRoomDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PhotoRoomDatabase? = null

        fun getDatabase(context: Context): PhotoRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhotoRoomDatabase::class.java,
                    "photo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}