package com.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


import java.util.*

// Entidade a ser mapeada no BD.
// Parcelable pra lidar com a entidade entre telas

@Parcelize
@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey val id: UUID,
    val nome: String,
    val descricao: String,
    val data: Date,
    val concluida : Boolean
) : Parcelable
