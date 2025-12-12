package com.example.finaltermdatabase

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_3_4 = object : Migration(startVersion = 3, endVersion = 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE todo_table ADD COLUMN is_favorite Integer NOT NULL Default 0")
    }
}

@TypeConverters(DateTypeConverter::class)
@Database(
    entities = [Todo::class], version = 4,
    autoMigrations = [AutoMigration(
        from = 2,
        to = 3,
        spec = TodoDatabase.CreatedAtNameChangeAutoMigration::class
    )]
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDAO

    @RenameColumn(
        fromColumnName = "createdAt",
        toColumnName = "created_at",
        tableName = "todo_table"
    )
    class CreatedAtNameChangeAutoMigration : AutoMigrationSpec {
    }

    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    name = "todo_database",
                    klass = TodoDatabase::class.java
                )
                    .addMigrations(MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}