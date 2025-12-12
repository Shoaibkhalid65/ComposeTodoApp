package com.example.finaltermdatabase

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws


@RunWith(AndroidJUnit4::class)
class TodoMigrationTesting {
    private val DB_NAME="migration_test"

    @get:Rule
    val helper: MigrationTestHelper= MigrationTestHelper(
        instrumentation = InstrumentationRegistry.getInstrumentation(),
        databaseClass = TodoDatabase::class.java,
        openFactory = FrameworkSQLiteOpenHelperFactory(),
        specs = listOf(TodoDatabase.CreatedAtNameChangeAutoMigration())
    )


    @Test
    @Throws(IOException::class)
    fun migrate1To2(){
        var db=helper.createDatabase(DB_NAME,1).apply {
            execSQL("INSERT INTO todo_table VALUES('title test','text test')")
            close()
        }
        db=helper.runMigrationsAndValidate(DB_NAME,3,true,MIGRATION_3_4)

        db.query("Select * from todo_table").apply {
            assert(moveToFirst())
            assert(getLong(getColumnIndex("createdAt"))==0L)
        }

    }

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(DB_NAME, 3).apply {
            close()
        }

        // Open latest version of the database. Room validates the schema
        // once all migrations execute.
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TodoDatabase::class.java,
            DB_NAME
        ).addMigrations(MIGRATION_3_4).build().apply {
            openHelper.writableDatabase.close()
        }
    }





}