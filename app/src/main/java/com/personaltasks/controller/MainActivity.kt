package com.personaltasks.controller

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.personaltasks.R
import com.personaltasks.view.TarefaAdapter
import com.personaltasks.model.Tarefa


class MainActivity : AppCompatActivity(), TarefaAdapter.OnItemLongClickListener {

    private val tarefas = mutableListOf<Tarefa>()
    private lateinit var adapter : TarefaAdapter
    private var selectedPosition = -1
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TarefaAdapter(tarefas, this)

        /*
           * Cria a lista tarefas e passa para o TarefaAdapter
           * RecycleView usa esse adapter p mostrar cada tarefa usando o layout do item_tarefa.xml
           * Quando o usuario cria na CadastroActivity ela é retornada para a Main
           * Adiciona a tarefa na lista e "avisa" o adapter para atualizar na tela

         */

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTarefas);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        registerForContextMenu(recyclerView)

        // launcher para receber resultado da segunda activity
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val tarefa = result.data?.getSerializableExtra("tarefa") as? Tarefa
                tarefa?.let {
                    if(selectedPosition >= 0){
                        tarefas[selectedPosition] = it
                        adapter.notifyItemChanged(selectedPosition);
                    }
                    else{
                        tarefas.add(it)
                        adapter.notifyItemInserted(tarefas.size - 1)
                    }
                    selectedPosition = -1
                }
            }
        }

        findViewById<Button>(R.id.botaoCadastro).setOnClickListener {
            selectedPosition = -1
            val intent = Intent(this, CadastroActivity::class.java)
            launcher.launch(intent)
        }

        }


    override fun onItemLongClick(view: View, position: Int) {
        selectedPosition = position
        view.showContextMenu() // mostra o menu de contexto
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // menu já criado no adapter, não precisa criar aqui
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val tarefa = tarefas.getOrNull(selectedPosition) ?: return super.onContextItemSelected(item)

        when (item.itemId) {
            R.id.menu_editar -> {
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                intent.putExtra("acao", "editar")
                launcher.launch(intent)
                return true
            }
            R.id.menu_excluir -> {
                tarefas.removeAt(selectedPosition)
                adapter.notifyItemRemoved(selectedPosition)
                selectedPosition = -1
                return true
            }
            R.id.menu_detalhes -> {
                val intent = Intent(this, CadastroActivity::class.java)
                intent.putExtra("tarefa", tarefa)
                intent.putExtra("acao", "detalhes")
                launcher.launch(intent)
                return true
            }
        }
        return super.onContextItemSelected(item)
    }



    }