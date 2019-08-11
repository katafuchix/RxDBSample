package com.example.rxdb.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(
                @PrimaryKey
                @ColumnInfo(name = "userid")
                val id: String = UUID.randomUUID().toString(),

                @ColumnInfo(name = "username")
                val userName: String
                )

/*
@Entity(tableName = "users")
class User {

    @PrimaryKey
    @ColumnInfo(name = "userid")
    var id: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "username")
    var userName: String = ""
}
*/