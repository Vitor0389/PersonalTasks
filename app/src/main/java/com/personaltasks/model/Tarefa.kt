package com.personaltasks.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Tarefa(
    val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val data: Long = 0L,
    val concluida: Boolean = false,
    val excluida: Boolean = false,
) : Parcelable
