package com.example.getuplord

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PhotoRepository(private val photoDao: PhotoDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val headwearPhotos: LiveData<List<Photo>> = photoDao.getAllHeadwearPhotos()

    val shirtPhotos: LiveData<List<Photo>> = photoDao.getAllShirtPhotos()

    val coatPhotos: LiveData<List<Photo>> = photoDao.getAllCoatPhotos()

    val pantsPhotos: LiveData<List<Photo>> = photoDao.getAllPantsPhotos()

    val socksPhotos: LiveData<List<Photo>> = photoDao.getAllSocksPhotos()

    val shoesPhotos: LiveData<List<Photo>> = photoDao.getAllShoesPhotos()

    suspend fun insert(photo: Photo) {
        photoDao.insert(photo)
    }
}