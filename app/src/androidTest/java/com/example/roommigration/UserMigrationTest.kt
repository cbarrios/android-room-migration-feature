package com.example.roommigration

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.roommigration.room.Database
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test.db"

@RunWith(AndroidJUnit4::class)
class UserMigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        Database::class.java,
        listOf(Database.Migration2To3()),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1to2_containsCorrectData() {
        // Given
        helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO user VALUES('test@test.com', 'Carlos')")
            close()
        }

        // When
        val db = helper.runMigrationsAndValidate(DB_NAME, 2, true)

        // Then
        db.query("SELECT * FROM user").apply {
            assertThat(moveToFirst()).isTrue()
            assertThat(getString(getColumnIndex("username"))).isEqualTo("Carlos")
            assertThat(getLong(getColumnIndex("created"))).isEqualTo(0)
        }
    }

    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).apply { close() }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            Database::class.java,
            DB_NAME
        ).addMigrations(Database.migration3To4).build().apply {
            openHelper.writableDatabase.close()
        }
    }
}