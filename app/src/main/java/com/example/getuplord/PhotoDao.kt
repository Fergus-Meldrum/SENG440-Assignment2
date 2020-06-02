package com.example.getuplord

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {

    @Query("SELECT * from photo_table WHERE type='Head-Wear'")
    fun getAllHeadwearPhotos(): LiveData<List<Photo>>

    @Query("SELECT * from photo_table WHERE type='Shirt'")
    fun getAllShirtPhotos(): LiveData<List<Photo>>

    @Query("SELECT * from photo_table WHERE type='Coat'")
    fun getAllCoatPhotos(): LiveData<List<Photo>>

    @Query("SELECT * from photo_table WHERE type='Pants'")
    fun getAllPantsPhotos(): LiveData<List<Photo>>

    @Query("SELECT * from photo_table WHERE type='Socks'")
    fun getAllSocksPhotos(): LiveData<List<Photo>>

    @Query("SELECT * from photo_table WHERE type='Shoes'")
    fun getAllShoesPhotos(): LiveData<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Photo)

    @Query("DELETE FROM photo_table")
    suspend fun deleteAll()
}