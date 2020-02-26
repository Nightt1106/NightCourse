package com.night.nightcourse.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LikeAnimalDao {
    @Query("SELECT * FROM 'animals'")
    fun getAll() : LiveData<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)
}