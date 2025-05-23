package com.personaltasks.model

import androidx.room.*

// DAO que faz a comunicação com o BD
@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefas")
    suspend fun listar(): List<Tarefa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(tarefa: Tarefa)

    @Update
    suspend fun atualizar(tarefa: Tarefa)
    @Delete
    suspend fun excluir(tarefa: Tarefa)
}

