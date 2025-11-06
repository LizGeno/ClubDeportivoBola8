package com.example.clubdeportivobola8.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.clubdeportivobola8.data.model.Socio
import java.util.Date
class DBHelper private constructor(context: Context) : SQLiteOpenHelper(context, "ClubDeportBola8.db", null, 2) {
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
            "fecha_nacimiento INTEGER, " +
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
        //db.execSQL( SQL_CREATE_TABLE_NO_SOCIOS)
        db.execSQL( SQL_CREATE_TABLE_ACTIVIDADES)
    }

    override fun onUpgrade( db: SQLiteDatabase,  oldVersion: Int,  newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS socios")
        db.execSQL("DROP TABLE IF EXISTS actividades")
        onCreate(db)
    }

    fun insertSocio(socio: Socio): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("dni", socio.dni)
            put("fecha_nacimiento", socio.fechaNacimiento.time)
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
                fechaNacimiento = Date(cursor.getLong(4)), //fechaNacimiento
                cursor.getString(5)  // email
            )
            socios.add(socio)
        }
        return socios
    }
    fun deleteSocio(socioId: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("socios", "id = ?", arrayOf(socioId.toString()))
        return result > 0
    }
    fun updateSocio(socio: Socio): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("dni", socio.dni)
            put("fecha_nacimiento", socio.fechaNacimiento.time)
            put("email", socio.email)
        }

        // Actualiza la fila donde el 'id' coincida.
        // El método 'update' devuelve el número de filas afectadas.
        val result = db.update("socios", values, "id = ?", arrayOf(socio.id.toString()))
        return result > 0
    }

    /**
     * Obtiene un único socio de la base de datos a partir de su ID.
     * Devuelve null si no se encuentra.
     */
    fun getSocioById(socioId: Int): Socio? {
        val db = this.readableDatabase
        var socio: Socio? = null
        val cursor = db.query(
            "socios",
            null, // todas las columnas
            "id = ?", // cláusula WHERE
            arrayOf(socioId.toString()), // argumentos del WHERE
            null,
            null,
            null
        )

        cursor.use { c ->
            if (c.moveToFirst()) { // Si se encuentra al menos una fila
                socio = Socio(
                    id = c.getInt(c.getColumnIndexOrThrow("id")),
                    nombre = c.getString(c.getColumnIndexOrThrow("nombre")),
                    apellido = c.getString(c.getColumnIndexOrThrow("apellido")),
                    dni = c.getString(c.getColumnIndexOrThrow("dni")),
                    fechaNacimiento = Date(c.getLong(c.getColumnIndexOrThrow("fecha_nacimiento"))),
                    email = c.getString(c.getColumnIndexOrThrow("email"))
                )
            }
        }
        return socio
    }
}