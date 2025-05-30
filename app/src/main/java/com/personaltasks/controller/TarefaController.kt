package com.personaltasks.controller


import android.content.Context
import com.personaltasks.model.Tarefa
import com.personaltasks.model.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// TESTANDO PUSH

// Controller p lidar com DAO
class TarefaController(context: Context) {
    private val tarefaDao = AppDatabase.getDatabase(context).tarefaDao()

    suspend fun listar(): List<Tarefa> = withContext(Dispatchers.IO) {
        tarefaDao.listar()
    }

    suspend fun inserir(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        tarefaDao.inserir(tarefa)
    }

    suspend fun excluir(tarefa: Tarefa) = withContext(Dispatchers.IO) {
        tarefaDao.excluir(tarefa)
    }

    suspend fun atualizar(tarefa: Tarefa) = withContext(Dispatchers.IO){
        tarefaDao.atualizar(tarefa)
    }
}
