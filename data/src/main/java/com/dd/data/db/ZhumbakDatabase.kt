package com.dd.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dd.data.db.dao.ZhumbakDbDao
import com.dd.data.db.entities.ZhumbakDbData


@Database(
        entities = [
            ZhumbakDbData::class
        ],
        version = 1,
        exportSchema = false
)
abstract class ZhumbakDatabase : RoomDatabase() {
    abstract fun zhumbakDao(): ZhumbakDbDao
}