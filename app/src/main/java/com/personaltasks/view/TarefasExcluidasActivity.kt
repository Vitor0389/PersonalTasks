package com.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.controller.TarefaController
import com.personaltasks.model.Tarefa

class TarefasExcluidasActivity : AppCompatActivity(), TarefaExcluidaAdapter.OnItemLongClickListener {

    private val tarefasExcluidas = mutableListOf<Tarefa>()
    private lateinit var adapter: TarefaExcluidaAdapter
    private var selectedPosition = -1

    private lateinit var tarefaController: TarefaController
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefas_excluidas)

        tarefaController = TarefaController(this)

        adapter = TarefaExcluidaAdapter(tarefasExcluidas, this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTarefasExcluidas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(recyclerView)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val tarefa = result.data?.getParcelableExtra<Tarefa>("tarefa")
                tarefa?.let {
                    // Após reativar ou editar, atualiza a tarefa
                    tarefaController.atualizar(it)
                    selectedPosition = -1
                }
            }
        }

        // Carrega só tarefas excluídas
        tarefaController.setOnTarefasChangedListener { lista ->
            tarefasExcluidas.clear()
            tarefasExcluidas.addAll(lista.filter { it.excluida })
            adapter.notifyDataSetChanged()
        }
        tarefaController.listar {}
    }

    override fun onItemLongClick(view: View, position: Int) {
        selectedPosition = position
        view.showContextMenu()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // O menu é criado no adapter
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefasExcluidas.getOrNull(selectedPosition) ?: return super.onContextItemSelected(item)
        return when (item.itemId) {
            R.id.menu_reativar -> {
                val tarefaReativada = tarefa.copy(excluida = false)
                val intent = Intent(this, CadastroActivity::class.java).apply {
                    putExtra("tarefa", tarefaReativada)
                    putExtra("acao", "editar")  // permite edição depois de reativar
                }
                launcher.launch(intent)
                true
            }
            R.id.menu_detalhes -> {
                val intent = Intent(this, CadastroActivity::class.java).apply {
                    putExtra("tarefa", tarefa)
                    putExtra("acao", "detalhes")
                }
                launcher.launch(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
