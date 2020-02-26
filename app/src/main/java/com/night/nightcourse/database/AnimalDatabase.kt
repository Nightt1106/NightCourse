package com.night.nightcourse.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.night.nightcourse.model.Animal
import com.night.nightcourse.model.AnimalDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Animal::class],version = 1,exportSchema = false)
abstract class AnimalDatabase : RoomDatabase(){

    abstract fun animalDao() : AnimalDao

    private class DatabaseCallback(private val scope: CoroutineScope) :RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
        }
    }

    companion object {
        const val DATABASE_NAME = "Animals"
        @Volatile
        private var INSTANCE: AnimalDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AnimalDatabase {
            return AnimalDatabase.INSTANCE ?: synchronized(this){
                val instance : AnimalDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(AnimalDatabase.DatabaseCallback(scope))
                    .build()
                AnimalDatabase.INSTANCE = instance
                instance
            }
        }
    }
}


