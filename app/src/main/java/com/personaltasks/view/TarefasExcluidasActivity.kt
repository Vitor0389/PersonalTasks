package com.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.controller.TarefaController
import com.personaltasks.model.Tarefa

class TarefasExcluidasActivity : AppCompatActivity(), TarefaExcluidaAdapter.OnItemLongClickListener {

    private val tarefasExcluidas = mutableListOf<Tarefa>()
    private lateinit var adapter: TarefaExcluidaAdapter
    private lateinit var tarefaController: TarefaController
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_excluidas)

        tarefaController = TarefaController(this)
        adapter = TarefaExcluidaAdapter(tarefasExcluidas, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewExcluidas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(recyclerView)

        tarefaController.setOnTarefasExcluidasChangedListener { lista ->
            tarefasExcluidas.clear()
            tarefasExcluidas.addAll(lista)
            adapter.notifyDataSetChanged()
        }

        tarefaController.listarExcluidas {
                lista ->
            runOnUiThread {
                tarefasExcluidas.clear()
                tarefasExcluidas.addAll(lista)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        selectedPosition = position
        view.showContextMenu()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Tarefa excluída")
        menu.add(0, R.id.menu_reativar, 0, "Reativar tarefa")
        menu.add(0, R.id.menu_detalhes, 1, "Detalhes")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefasExcluidas.getOrNull(selectedPosition) ?: return super.onContextItemSelected(item)
        return when (item.itemId) {
            R.id.menu_reativar -> {
                val reativada = tarefa.copy(excluida = false)
                tarefaController.atualizar(reativada)
                Toast.makeText(this, "Tarefa reativada", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_detalhes -> {
                val intent = Intent(this, CadastroActivity::class.java).apply {
                    putExtra("tarefa", tarefa)
                    putExtra("acao", "detalhes")
                }
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
