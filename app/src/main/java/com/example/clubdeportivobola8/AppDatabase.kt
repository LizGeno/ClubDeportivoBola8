package com.example.clubdeportivobola8

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cuota::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cuotaDao(): CuotaDao

    companion object {
        // La instancia de la base de datos. Es volátil para asegurar que sea siempre la más reciente.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si la instancia ya existe, la devolvemos. Si no, la creamos de forma segura.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "club_database"
                )
                // Opcional: Estrategia de migración si cambias la estructura de la BD en el futuro.
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
