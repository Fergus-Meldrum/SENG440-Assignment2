package com.example.getuplord

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * photo object: represents all information about a chothing item. instances are stored in the database
 */
@Entity(tableName = "photo_table")
class Photo(@PrimaryKey @ColumnInfo(name = "photo_name") val name: String, val type :String, val location: String) {
}