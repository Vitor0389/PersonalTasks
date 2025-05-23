package com.personaltasks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey val id: UUID,
    val nome: String,
    val descricao: String,
    val data: Date
) : Serializable
