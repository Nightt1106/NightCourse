package com.night.nightcourse.viewmodel

import android.app.Application


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.viewModelScope
import com.night.nightcourse.Repository
import com.night.nightcourse.database.AnimalDatabase

import com.night.nightcourse.database.LikeAnimalDatabase
import com.night.nightcourse.model.Animal
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch



class AnimalViewModel(application: Application) : AndroidViewModel(application){


    private val repository : Repository
    var tempAnimals : LiveData<List<Animal>>
    val animals: LiveData<List<Animal>>
    val likeAnimals : LiveData<List<Animal>>
    val animalsOfDog : LiveData<List<Animal>>
    val animalsOfCat : LiveData<List<Animal>>
    var mutableMap : MutableMap<Int,Int> = mutableMapOf()

    init {
        val likeAnimalDao = LikeAnimalDatabase.getDatabase(application,viewModelScope).likeAnimalDao()
        val animalDao = AnimalDatabase.getDatabase(application,viewModelScope).animalDao()
        repository = Repository(animalDao,likeAnimalDao)
        animals = repository.animals
        tempAnimals = repository.animals
        likeAnimals = repository.likeAnimals
        animalsOfDog = repository.animalOfDogs
        animalsOfCat = repository.animalOfCats
    }

    fun insert(animal: Animal) = viewModelScope.launch {
        repository.insert(animal)
    }

    fun delete(animal: Animal) = viewModelScope.launch {
        repository.delete(animal)
    }

    //----------------------------------------------------

    private suspend fun loadAnimals(){
        val temp:List<Animal> = repository.getAnimalsFromUrl()
        repository.deleteAll()
        repository.insertAll(temp)
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            loadAnimals()
        }
    }
}