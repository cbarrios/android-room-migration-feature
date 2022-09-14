package com.example.roommigration.room

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roommigration.room.school.School
import com.example.roommigration.room.school.SchoolDao
import com.example.roommigration.room.user.User
import com.example.roommigration.room.user.UserDao
import com.example.roommigration.room.Database as MyDatabase

@Database(
    entities = [
        User::class,
        School::class
    ],
    version = 4,
    autoMigrations = [
        // added new entity field
        AutoMigration(from = 1, to = 2),
        // modified existing entity field
        AutoMigration(from = 2, to = 3, spec = MyDatabase.Migration2To3::class)
    ]
)
abstract class Database : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val schoolDao: SchoolDao

    // 2 to 3
    @RenameColumn(tableName = "User", fromColumnName = "created", toColumnName = "createdAt")
    class Migration2To3 : AutoMigrationSpec
    // We could also use auto migrations to delete fields and both rename and delete tables but not for adding tables
    // @DeleteColumn
    // @RenameTable
    // @DeleteTable

    // 3 to 4 (adding a new table must be a manual migration). Refer to MainActivity
    companion object {
        val migration3To4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS school (name CHAR NOT NULL PRIMARY KEY)")
            }
        }
    }
}