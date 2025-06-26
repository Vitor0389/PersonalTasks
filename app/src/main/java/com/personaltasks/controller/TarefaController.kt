package com.personaltasks.controller

import android.content.Context
import com.personaltasks.model.TarefaDaoFirebase
import com.personaltasks.model.TarefaFirebaseDatabase
import com.personaltasks.model.Tarefa

class TarefaController(context: Context) {

    private val tarefaDao: TarefaDaoFirebase = TarefaFirebaseDatabase()

    fun listar(callback: (List<Tarefa>) -> Unit) {
        tarefaDao.listar(callback)
    }

    fun inserir(tarefa: Tarefa) {
        tarefaDao.inserir(tarefa)
    }

    fun atualizar(tarefa: Tarefa) {
        tarefaDao.atualizar(tarefa)
    }

    fun excluir(tarefa: Tarefa) {
        tarefaDao.excluir(tarefa)
    }
}
