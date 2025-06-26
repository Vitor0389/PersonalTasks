package com.personaltasks.model

interface TarefaDaoFirebase {
    fun listar(callback: (List<Tarefa>) -> Unit)
    fun inserir(tarefa: Tarefa)
    fun atualizar(tarefa: Tarefa)
    fun excluir(tarefa: Tarefa)
}
