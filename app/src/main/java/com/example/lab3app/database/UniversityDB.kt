package com.example.lab3app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lab3app.data.Faculty
import com.example.lab3app.data.Group
import com.example.lab3app.data.Student
import com.example.lab3app.data.University


@Database(
    entities = [University::class, Faculty::class, Group::class, Student::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(UniversityTypeConverter::class)

abstract class UniversityDB: RoomDatabase() {
    abstract fun universityDAO(): UniversityDAO

    companion object{
        @Volatile
        private var INSTANCE: UniversityDB? = null

        fun getDatabase(context: Context): UniversityDB{
            return INSTANCE ?: synchronized(this){
                buildDatabase(context).also{ INSTANCE=it}
            }
        }

//    val MIGRAION_2_3 = object : Migration(2,3){
//        override fun migrate(db: SupportSQLiteDatabase) {
//            TODO("Not yet implemented")
//        }
//    }

    private fun buildDatabase(context: Context) = Room.databaseBuilder(
        context,
        UniversityDB::class.java,
        "university_database")
        .fallbackToDestructiveMigration()
//        .addMigrations(MIGRATION_2_3)
        .build()

    }
}