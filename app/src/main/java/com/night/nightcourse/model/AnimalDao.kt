package com.night.nightcourse.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AnimalDao{

    @Query("SELECT * FROM animals WHERE animal_kind = '貓'")
    fun getCats() : LiveData<List<Animal>>

    @Query("SELECT * FROM animals")
    fun getAll() : LiveData<List<Animal>>

    @Query("SELECT * FROM 'animals' WHERE animal_kind = '狗'")
    fun getDogs() : LiveData<List<Animal>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(animalsList : List<Animal>)

    @Query("Delete FROM animals")
    fun deleteAll()
}