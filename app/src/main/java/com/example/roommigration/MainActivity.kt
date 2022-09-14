package com.example.roommigration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.roommigration.room.Database
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "users.db"
        ).addMigrations(Database.migration3To4).build()

        //readFirstUsers(db)
        //insertFirstUsers(db)

        readSchools(db)
        //insertSchools(db)
    }

//    private fun readFirstUsers(db: Database) {
//        lifecycleScope.launch {
//            db.userDao.getUsers().forEach(::println)
//        }
//    }

//    private fun insertFirstUsers(db: Database) {
//        (1..10).forEach {
//            lifecycleScope.launch {
//                db.userDao.insertUser(
//                    User(
//                        email = "test@test$it.com",
//                        username = "test$it"
//                    )
//                )
//            }
//        }
//    }

    private fun readSchools(db: Database) {
        lifecycleScope.launch {
            db.schoolDao.getSchools().forEach(::println)
        }
    }

//    private fun insertSchools(db: Database) {
//        (1..10).forEach {
//            lifecycleScope.launch {
//                db.schoolDao.insertSchool(
//                    School(name = "School$it")
//                )
//            }
//        }
//    }
}