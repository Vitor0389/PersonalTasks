package com.personaltasks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.model.Tarefa
import java.text.SimpleDateFormat
import java.util.Locale


// Adapter que conecta minha lista de tarefas com o recyclerView
class TarefaAdapter(
    private val listaTarefas: MutableList<Tarefa>
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    // ViewHolder guarda as referências dos componentes do item_tarefa.xml
    class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.textTitulo)
        val descricao: TextView = itemView.findViewById(R.id.textDescricao)
        val dataLimite: TextView = itemView.findViewById(R.id.textDataLimite)
    }

    // Cria a view para cada item, com base no item_tarefa.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    // Ligo os dados do objeto Tarefa com os componentes do layout
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]

        holder.titulo.text = tarefa.nome
        holder.descricao.text = tarefa.descricao

        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.dataLimite.text = "Data limite: ${formato.format(tarefa.data)}"
    }

    // Conta quantos itens tem na lista
    override fun getItemCount(): Int = listaTarefas.size
}
