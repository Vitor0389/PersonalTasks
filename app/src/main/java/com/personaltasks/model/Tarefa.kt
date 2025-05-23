package com.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


import java.util.*

@Parcelize
@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey val id: UUID,
    val nome: String,
    val descricao: String,
    val data: Date
) : Parcelable
