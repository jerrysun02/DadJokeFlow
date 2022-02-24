package com.langlab.dadjokeflow.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "tb_joke")
data class JokeDTO(
    @PrimaryKey val title: String,
    @ColumnInfo val content: String?
)