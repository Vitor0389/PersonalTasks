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

class TarefaExcluidaAdapter(
    private val listaTarefas: MutableList<Tarefa>,
    private val listener: OnItemLongClickListener
) : RecyclerView.Adapter<TarefaExcluidaAdapter.TarefaViewHolder>() {

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int)
    }

    inner class TarefaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        val titulo: TextView = itemView.findViewById(R.id.textTitulo)
        val descricao: TextView = itemView.findViewById(R.id.textDescricao)
        val dataLimite: TextView = itemView.findViewById(R.id.textDataLimite)

        init {
            itemView.setOnCreateContextMenuListener(this)
            itemView.setOnLongClickListener {
                listener.onItemLongClick(it, adapterPosition)
                true
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu.setHeaderTitle("Opções da tarefa excluída")
            menu.add(adapterPosition, R.id.menu_reativar, 0, "Reativar tarefa")
            menu.add(adapterPosition, R.id.menu_detalhes, 1, "Detalhes")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarefa, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]
        holder.titulo.text = tarefa.nome
        holder.descricao.text = tarefa.descricao
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        holder.dataLimite.text = "Data limite: ${formato.format(tarefa.data)}"
    }

    override fun getItemCount(): Int = listaTarefas.size
}
