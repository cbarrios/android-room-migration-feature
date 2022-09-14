package com.example.roommigration.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.example.roommigration.room.user.User
import com.example.roommigration.room.user.UserDao
import com.example.roommigration.room.Database as MyDatabase

@Database(
    entities = [User::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = MyDatabase.Migration2To3::class)
    ]
)
abstract class Database : RoomDatabase() {

    abstract val userDao: UserDao

    @RenameColumn(tableName = "User", fromColumnName = "created", toColumnName = "createdAt")
    class Migration2To3 : AutoMigrationSpec
}