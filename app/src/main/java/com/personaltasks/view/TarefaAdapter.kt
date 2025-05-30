package com.personaltasks.view

import android.view.ContextMenu
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
    private val listaTarefas: MutableList<Tarefa>,
    private val listener : OnItemLongClickListener
) : RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>() {

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int);
    }

    // ViewHolder guarda as referências dos componentes do item_tarefa.xml
    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnCreateContextMenuListener{
        val titulo: TextView = itemView.findViewById(R.id.textTitulo)
        val descricao: TextView = itemView.findViewById(R.id.textDescricao)
        val dataLimite: TextView = itemView.findViewById(R.id.textDataLimite)
        val selectedState : TextView = itemView.findViewById(R.id.textEstado)

        init {
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener{
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
           menu.setHeaderTitle("Opções da tarefa")
           menu.add(adapterPosition, R.id.menu_editar, 0, "Editar tarefas");
           menu.add(adapterPosition, R.id.menu_excluir, 1, "Excluir tarefa")
           menu.add(adapterPosition, R.id.menu_detalhes, 2, "Detalhes")
        }
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

        if(tarefa.concluida){
            holder.selectedState.text = "Tarefa Concluida"
        }
        else{
            holder.selectedState.text = "Tarefa Pendente"
        }
    }

    // Conta quantos itens tem na lista
    override fun getItemCount(): Int = listaTarefas.size


}
