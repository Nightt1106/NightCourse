package com.night.nightcourse

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.night.nightcourse.model.Animal
import com.night.nightcourse.model.AnimalDao
import com.night.nightcourse.model.LikeAnimalDao
import java.net.URL



class Repository(private val animalDao: AnimalDao,private val likeAnimalDao: LikeAnimalDao){

    private val TAG = Repository::class.java.simpleName
    private val dataUrl = "https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL"


    fun getAnimalsFromUrl() : List<Animal>{
        val result = URL(dataUrl).readText()
        return Gson().fromJson(result, object : TypeToken<List<Animal>>() {}.type)
    }

    val animals = animalDao.getAll()
    val animalOfDogs = animalDao.getDogs()
    val animalOfCats = animalDao.getCats()

    suspend fun insertAll(animalsList : List<Animal>){
        animalDao.insertAll(animalsList)
    }

    suspend fun deleteAll(){
        animalDao.deleteAll()
    }

    //`````````````````````````````````````````

    val likeAnimals : LiveData<List<Animal>> = likeAnimalDao.getAll()

    suspend fun insert(animal: Animal){
        likeAnimalDao.insert(animal)
    }

    suspend fun delete(animal: Animal){
        likeAnimalDao.delete(animal)
    }

}