package com.personaltasks.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.personaltasks.R
import com.personaltasks.controller.TarefaController
import com.personaltasks.model.Tarefa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TESTE PUSH
class MainActivity : AppCompatActivity(), TarefaAdapter.OnItemLongClickListener {

    private val tarefas = mutableListOf<Tarefa>()
    private lateinit var adapter: TarefaAdapter
    private var selectedPosition = -1
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var tarefaController: TarefaController

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
                    if (selectedPosition >= 0) {
                        tarefaController.atualizar(it)
                    } else {
                        tarefaController.inserir(it)
                    }
                    selectedPosition = -1
                }
            }
        }

        val botaoCadastro = findViewById<Button>(R.id.botaoCadastro)
        botaoCadastro.setOnClickListener {
            selectedPosition = -1
            val intent = Intent(this, CadastroActivity::class.java)
            launcher.launch(intent)
        }

        val botaoTarefasExcluidas = findViewById<Button>(R.id.botaoTarefasExcluidas)
        botaoTarefasExcluidas.setOnClickListener {
            val intent = Intent(this, TarefasExcluidasActivity::class.java)
            startActivity(intent)
        }

        tarefaController.setOnTarefasChangedListener { lista ->
            lifecycleScope.launch(Dispatchers.Main) {
                tarefas.clear()
                tarefas.addAll(lista)
                adapter.notifyDataSetChanged()
            }
        }
        tarefaController.listar {}
    }



    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onItemLongClick(view: View, position: Int) {
        selectedPosition = position
        view.showContextMenu()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Menu criado no adapter
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefas.getOrNull(selectedPosition) ?: return super.onContextItemSelected(item)
        return when (item.itemId) {
            R.id.menu_editar -> {
                val intent = Intent(this, CadastroActivity::class.java).apply {
                    putExtra("tarefa", tarefa)
                    putExtra("acao", "editar")
                }
                launcher.launch(intent)
                true
            }
            R.id.menu_excluir -> {
                tarefaController.excluir(tarefa)
                // A lista será atualizada via listener do controller, por isso não precisa atualizar adapter aqui
                selectedPosition = -1
                Toast.makeText(this, "Tarefa excluída", Toast.LENGTH_SHORT).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_opcoes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_nova_tarefa -> {
                selectedPosition = -1
                val intent = Intent(this, CadastroActivity::class.java)
                launcher.launch(intent)
                true
            }

            R.id.action_tarefas_excluidas -> {
                startActivity(Intent(this, TarefasExcluidasActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
