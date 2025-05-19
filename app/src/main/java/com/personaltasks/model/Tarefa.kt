package com.personaltasks.model

import java.io.Serializable
import java.util.*

// Representa a tabela no banco de dados
data class Tarefa(
    val id: UUID,
    val nome: String,
    val descricao: String,
    val data: Date
) : Serializable {

}
