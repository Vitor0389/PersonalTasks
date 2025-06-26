    package com.personaltasks.model

    import com.google.firebase.database.*
    import com.google.firebase.database.ktx.getValue

    class TarefaFirebaseDatabase : TarefaDaoFirebase {

        private val database = FirebaseDatabase.getInstance()
        private val referencia = database.getReference("tarefas")

        override fun listar(callback: (List<Tarefa>) -> Unit) {
            referencia.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lista = mutableListOf<Tarefa>()
                    snapshot.children.forEach { filho ->
                        val tarefa = filho.getValue<Tarefa>()
                        tarefa?.let { lista.add(it) }
                    }
                    callback(lista)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(emptyList())
                }
            })
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
