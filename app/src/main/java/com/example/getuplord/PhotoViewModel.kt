package com.example.getuplord

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PhotoRepository


    val headwearPhotos: LiveData<List<Photo>>
    val shirtPhotos: LiveData<List<Photo>>
    val coatPhotos: LiveData<List<Photo>>
    val pantsPhotos: LiveData<List<Photo>>
    val socksPhotos: LiveData<List<Photo>>
    val shoesPhotos: LiveData<List<Photo>>

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