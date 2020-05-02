package com.dd.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.dd.data.db.entities.ZhumbakDbData
import com.dd.domain.model.ZhumbakModel

@Dao
interface ZhumbakDbDao : BaseDbDao<ZhumbakDbData> {

    @Query("SELECT * FROM ${ZhumbakDbData.TABLE_NAME} ORDER BY RANDOM() LIMIT 1")
    fun getRandomZhumbak(): ZhumbakModel
}