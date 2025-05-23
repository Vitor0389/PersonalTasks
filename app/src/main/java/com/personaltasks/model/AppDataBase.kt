package com.personaltasks.model

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverters

@Database(entities = [Tarefa::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tarefas_db"
                ).build().also { instance = it }
            }
    }
}
