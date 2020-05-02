package com.dd.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = ZhumbakDbData.TABLE_NAME)
data class ZhumbakDbData(

    @PrimaryKey
    @ColumnInfo(name = ID) val id: Long,
    @ColumnInfo(name = ZHUMBAK_QUESTION) val zhumbak_question: String,
    @ColumnInfo(name = ZHUMBAK_ANSWER) val zhumbak_answer: String
) {

    //////////////////////////TABLE///////////////////////////

    companion object {
        const val TABLE_NAME = "zhumbak"
        const val ID = "id"
        const val ZHUMBAK_QUESTION = "question"
        const val ZHUMBAK_ANSWER = "answer"
    }
    //////////////////////////////////////////////////////////

}

