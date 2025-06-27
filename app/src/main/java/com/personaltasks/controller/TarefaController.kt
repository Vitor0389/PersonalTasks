    package com.personaltasks.controller

    import android.content.Context
    import com.personaltasks.model.TarefaDaoFirebase
    import com.personaltasks.model.TarefaFirebaseDatabase
    import com.personaltasks.model.Tarefa

    class TarefaController(context: Context) {

        private val tarefaDao: TarefaDaoFirebase = TarefaFirebaseDatabase()

        private var tarefasListener: ((List<Tarefa>) -> Unit)? = null

        private var tarefasExcluidasListener: ((List<Tarefa>) -> Unit)? = null

        fun listar(callback: (List<Tarefa>) -> Unit) {
            tarefaDao.listar {
                val naoExcluidas = it.filter { tarefa -> !tarefa.excluida }
                tarefasListener?.invoke(naoExcluidas)
            }
        }

        fun inserir(tarefa: Tarefa) {
            tarefaDao.inserir(tarefa)
        }

        fun atualizar(tarefa: Tarefa) {
            tarefaDao.atualizar(tarefa)
        }

        fun excluir(tarefa: Tarefa) {
            val excluida = tarefa.copy(excluida = true)
            atualizar(excluida)
        }

        fun listarExcluidas(callback: (List<Tarefa>) -> Unit) {
            tarefaDao.listarExcluidas(callback)
        }

        fun setOnTarefasChangedListener(listener: (List<Tarefa>) -> Unit) {
            tarefasListener = listener
        }

        fun setOnTarefasExcluidasChangedListener(listener: (List<Tarefa>) -> Unit) {
            tarefasExcluidasListener = listener
        }


    }
