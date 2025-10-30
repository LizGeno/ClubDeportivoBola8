package com.example.clubdeportivobola8.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clubdeportivobola8.data.model.Socio

class DBHelper private constructor(context: Context) : SQLiteOpenHelper(context, "ClubDeportBola8.db", null, 1) {
    companion object {
        @Volatile
        private var INSTANCE: DBHelper? = null
        fun getInstance(context: Context): DBHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DBHelper(context.applicationContext).also { INSTANCE = it }
            }
        }
        private const val SQL_CREATE_TABLE_SOCIOS = "CREATE TABLE socios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "apellido TEXT NOT NULL, " +
            "dni TEXT NOT NULL, " +
            "fecha_nacimiento DATE NOT NULL, " +
            "email TEXT NOT NULL)"

        private const val  SQL_CREATE_TABLE_NO_SOCIOS = "CREATE TABLE no_socios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "apellido TEXT NOT NULL, " +
            "dni TEXT NOT NULL, " +
            "fecha_nacimiento DATE NOT NULL, " +
            "email TEXT NOT NULL)"
        private const val SQL_CREATE_TABLE_ACTIVIDADES = "CREATE TABLE actividades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "descripcion TEXT NOT NULL, " +
                "precio REAL NOT NULL)"
    }
    override fun onCreate(db: SQLiteDatabase) {
        //Tablas pasra Registro de los socios y de los no socios.
        db.execSQL( SQL_CREATE_TABLE_SOCIOS)
        db.execSQL( SQL_CREATE_TABLE_NO_SOCIOS)
        db.execSQL( SQL_CREATE_TABLE_ACTIVIDADES)
        //TODO Registro de actividades.
        //TODO Entrega de carnet (opcional) y cobro de cuota mensual (socio) o diaria (no socio).
        //TODO Listado diario de los socios que en la fecha les vence la cuota.
    }

    override fun onUpgrade( db: SQLiteDatabase,  oldVersion: Int,  newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS socios")
        onCreate(db)
    }

    fun insertSocio(socio: Socio): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("dni", socio.dni)
            put("fecha_nacimiento", socio.fechaNacimiento)
            put("email", socio.email)
        }
        val newRowId = db.insert("socios", null, values)
        return newRowId != -1L
        //No cerramos la base de datos: El Singleton se encargará de gestionar su ciclo de vida.
    }
    fun obtenerSocios(): List<Socio>{
        val socios = mutableListOf<Socio>()
        val db = this.readableDatabase
        val cursor = db.query("socios", null, null, null, null, null, null)
        while (cursor.moveToNext()){
            val socio = Socio(
                cursor.getInt(0), // Id
                cursor.getString(1), // nombre
                cursor.getString(2), // apellido
                cursor.getString(3), // dni
                cursor.getString(4), // fecha_nacimiento
                cursor.getString(5)  // email
            )
            socios.add(socio)
        }
        return socios
    }

}