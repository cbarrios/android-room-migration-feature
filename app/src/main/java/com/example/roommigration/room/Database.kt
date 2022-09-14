package com.example.roommigration.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roommigration.room.user.User
import com.example.roommigration.room.user.UserDao

@Database(
    entities = [User::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val userDao: UserDao
}