package com.personaltasks.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.personaltasks.R
import com.personaltasks.adapter.TarefaAdapter
import com.personaltasks.model.Tarefa


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
           * Cria a lista tarefas e passa para o TarefaAdapter
           * RecycleView usa esse adapter p mostrar cada tarefa usando o layout do item_tarefa.xml
           * Quando o usuario cria na CadastroActivity ela é retornada para a Main
           * Adiciona a tarefa na lista e "avisa" o adapter para atualizar na tela

         */
        val tarefas = mutableListOf<Tarefa>()
        val adapter = TarefaAdapter(tarefas)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTarefas);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val tarefa = result.data?.getSerializableExtra("tarefa") as? Tarefa
                tarefa?.let {
                    tarefas.add(it)
                    adapter.notifyItemInserted(tarefas.size - 1)
                }
            }
        }

        findViewById<Button>(R.id.botaoCadastro).setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            launcher.launch(intent)
        }

        }
    }