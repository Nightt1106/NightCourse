package com.night.nightcourse.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.night.nightcourse.model.Animal
import com.night.nightcourse.model.AnimalDao
import com.night.nightcourse.model.LikeAnimalDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Animal::class],version = 1,exportSchema = false)
abstract class LikeAnimalDatabase : RoomDatabase(){

    abstract fun likeAnimalDao() : LikeAnimalDao

    private class LikeDatabaseCallback(
        private val scope: CoroutineScope
    )  : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {it ->
                scope.launch {

                }
            }
        }
    }

    companion object{
        const val DATABASE_NAME = "Like_Animals"

        @Volatile
        private var INSTANCE : LikeAnimalDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope):LikeAnimalDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LikeAnimalDatabase::class.java,
                        DATABASE_NAME)
                    .addCallback(LikeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}