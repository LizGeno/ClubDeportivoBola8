package com.example.clubdeportivobola8

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CuotaDao {

    // --- Consulta para obtener todas las cuotas ---
    @Query("SELECT * FROM cuotas ORDER BY fechaVencimiento DESC")
    fun getAllCuotas(): Flow<List<Cuota>>

    // --- Consulta para buscar cuotas por fecha de vencimiento ---
    @Query("SELECT * FROM cuotas WHERE fechaVencimiento = :fecha")
    fun getCuotasByFechaVencimiento(fecha: String): Flow<List<Cuota>>

    // --- Inserción de nuevas cuotas ---
    @Insert
    suspend fun insertAll(vararg cuotas: Cuota)

    // --- Consulta para contar el número de cuotas (útil para saber si la BD está vacía) ---
    @Query("SELECT COUNT(*) FROM cuotas")
    suspend fun count(): Int
}
