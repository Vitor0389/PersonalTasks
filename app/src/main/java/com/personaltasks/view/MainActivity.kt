package com.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.controller.TarefaController
import com.personaltasks.model.AppDatabase
import com.personaltasks.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), TarefaAdapter.OnItemLongClickListener {

    private val tarefas = mutableListOf<Tarefa>()
    private lateinit var adapter: TarefaAdapter
    private var selectedPosition = -1
    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var tarefaController : TarefaController;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TarefaAdapter(tarefas, this)
        tarefaController = TarefaController(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTarefas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        registerForContextMenu(recyclerView)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val tarefa = result.data?.getParcelableExtra<Tarefa>("tarefa")
                tarefa?.let {
                    lifecycleScope.launch {
                        if (selectedPosition >= 0) {
                            // Atualiza no banco e na lista
                            tarefaController.atualizar(it)
                            tarefas[selectedPosition] = it
                            withContext(Dispatchers.Main) {
                                adapter.notifyItemChanged(selectedPosition)
                            }
                        } else {
                            // Insere no banco e na lista
                            tarefaController.inserir(it)
                            tarefas.add(it)
                            withContext(Dispatchers.Main) {
                                adapter.notifyItemInserted(tarefas.size - 1)
                            }
                        }
                        selectedPosition = -1
                    }
                }
            }
        }

        findViewById<Button>(R.id.botaoCadastro).setOnClickListener {
            selectedPosition = -1
            val intent = Intent(this, CadastroActivity::class.java)
            launcher.launch(intent)
        }

        // Carrega as tarefas do banco ao iniciar a activity
        carregarTarefas()
    }


    private fun carregarTarefas() {
        lifecycleScope.launch {
            val lista = tarefaController.listar();
            tarefas.clear()
            tarefas.addAll(lista)
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
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
        // menu criado no adapter
    }

    // Função pra manipular o menu de contexto
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefas.getOrNull(selectedPosition) ?: return super.onContextItemSelected(item)

        return when (item.itemId) {
            R.id.menu_editar -> {
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                intent.putExtra("acao", "editar")
                launcher.launch(intent)
                true
            }
            R.id.menu_excluir -> {
                lifecycleScope.launch {
                    tarefaController.excluir(tarefa)
                    tarefas.removeAt(selectedPosition)
                    withContext(Dispatchers.Main) {
                        adapter.notifyItemRemoved(selectedPosition)
                    }
                    selectedPosition = -1
                }
                true
            }
            R.id.menu_detalhes -> {
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                intent.putExtra("acao", "detalhes")
                launcher.launch(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
