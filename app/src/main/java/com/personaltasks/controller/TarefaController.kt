    package com.personaltasks.controller

    import android.content.Context
    import com.personaltasks.model.TarefaDaoFirebase
    import com.personaltasks.model.TarefaFirebaseDatabase
    import com.personaltasks.model.Tarefa

    class TarefaController(context: Context) {

        private val tarefaDao: TarefaDaoFirebase = TarefaFirebaseDatabase()

        private var tarefasListener: ((List<Tarefa>) -> Unit)? = null

        fun listar(callback: (List<Tarefa>) -> Unit) {
            tarefaDao.listar {
                tarefasListener?.invoke(it)
            }
        }

        fun inserir(tarefa: Tarefa) {
            tarefaDao.inserir(tarefa)
        }

        fun atualizar(tarefa: Tarefa) {
            tarefaDao.atualizar(tarefa)
        }

        fun excluir(tarefa: Tarefa) {
            tarefaDao.excluir(tarefa)
        }

        fun setOnTarefasChangedListener(listener: (List<Tarefa>) -> Unit) {
            tarefasListener = listener
        }


    }
