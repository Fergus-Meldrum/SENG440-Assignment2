package com.example.getuplord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Model: connects user view to the database
 */
class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PhotoRepository


    val headwearPhotos: LiveData<List<Photo>>
    val shirtPhotos: LiveData<List<Photo>>
    val coatPhotos: LiveData<List<Photo>>
    val pantsPhotos: LiveData<List<Photo>>
    val socksPhotos: LiveData<List<Photo>>
    val shoesPhotos: LiveData<List<Photo>>

    //retrieving lists of Photo objects from repository
    //a Photo object represents all information about a clothing item including where it's photo is stored
    init {
        val wordsDao = PhotoRoomDatabase.getDatabase(application).photoDao()
        repository = PhotoRepository(wordsDao)
        headwearPhotos = repository.headwearPhotos
        shirtPhotos = repository.shirtPhotos
        coatPhotos = repository.coatPhotos
        pantsPhotos = repository.pantsPhotos
        socksPhotos = repository.socksPhotos
        shoesPhotos = repository.shoesPhotos
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(photo: Photo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(photo)
    }
}