package com.personaltasks.model

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class   TarefaFirebaseDatabase : TarefaDaoFirebase {

    private val referencia = FirebaseDatabase.getInstance().getReference("tarefas")
    private val tarefaList = mutableListOf<Tarefa>()

    private var listener: ((List<Tarefa>) -> Unit)? = null

    init {
        referencia.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefa = snapshot.getValue<Tarefa>()
                tarefa?.let {
                    if (!tarefaList.any { it.id == tarefa.id }) {
                        tarefaList.add(tarefa)
                        listener?.invoke(tarefaList)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val tarefa = snapshot.getValue<Tarefa>()
                tarefa?.let {
                    val index = tarefaList.indexOfFirst { it.id == tarefa.id }
                    if (index != -1) {
                        tarefaList[index] = tarefa
                        listener?.invoke(tarefaList)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val tarefa = snapshot.getValue<Tarefa>()
                tarefa?.let {
                    tarefaList.removeIf { it.id == tarefa.id }
                    listener?.invoke(tarefaList)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })

        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tarefaList.clear()
                val tarefasMap = snapshot.getValue<Map<String, Tarefa>>()
                tarefasMap?.values?.let {
                    tarefaList.addAll(it)
                    listener?.invoke(tarefaList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun setOnTarefasChangedListener(callback: (List<Tarefa>) -> Unit) {
        listener = callback
    }

    override fun listar(callback: (List<Tarefa>) -> Unit) {
        setOnTarefasChangedListener(callback)
    }

    override fun inserir(tarefa: Tarefa) {
        referencia.child(tarefa.id.toString()).setValue(tarefa)
    }

    override fun atualizar(tarefa: Tarefa) {
        referencia.child(tarefa.id.toString()).setValue(tarefa)
    }

    override fun excluir(tarefa: Tarefa) {
        referencia.child(tarefa.id.toString()).removeValue()
    }
}
