package com.personaltasks.view
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.personaltasks.R
import com.personaltasks.R.*
import com.personaltasks.model.Tarefa
import java.util.Locale
import java.util.UUID

class CadastroActivity : AppCompatActivity() {

    // Referência dos componentes

    private var id : String = UUID.randomUUID().toString();
    private lateinit var editTextNome: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var selectedDateText: TextView
    private lateinit var buttonDatePicker: Button
    private lateinit var buttonSalvar: Button
    private lateinit var buttonCancelar: Button
    private lateinit var selectedState : TextView;
    private lateinit var selectedPrioridade : TextView;

    private lateinit var buttonConcluir : Button
    private lateinit var buttonDeixarPendente : Button
    private lateinit var buttonPrioridadeAlta : Button
    private lateinit var buttonPrioridadeMedia : Button
    private lateinit var buttonPrioridadeBaixa : Button



    private var dataSelecionada: Calendar? = null

    private var concluido : Boolean = false

    private var prioridadeAlta : Boolean = false

    private var prioridadeMedia : Boolean = false

    private var prioridadeBaixa : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {


            super.onCreate(savedInstanceState)
            setContentView(layout.activity_cadastro);

            editTextNome = findViewById(R.id.editText1)
            editTextDescricao = findViewById(R.id.editText2)
            selectedDateText = findViewById(R.id.selectedDateText)
            buttonDatePicker = findViewById(R.id.button_date_picker)
            buttonSalvar = findViewById(R.id.button2)
            buttonCancelar = findViewById(R.id.button3)

            buttonConcluir = findViewById(R.id.button_concluir_tarefa)
            buttonDeixarPendente = findViewById(R.id.button_deixar_pendente)
            buttonPrioridadeAlta = findViewById(R.id.button_prioridade_alta)
            buttonPrioridadeMedia = findViewById(R.id.button_prioridade_media)
            buttonPrioridadeBaixa = findViewById(R.id.button_prioridade_baixa)

            selectedState = findViewById(R.id.selectedStateText)
            selectedPrioridade = findViewById(R.id.selectedPrioridadeText)




            // Recebe tarefa e ação da intent
            val tarefa = intent.getParcelableExtra<Tarefa>("tarefa")
            val acao = intent.getStringExtra("acao") ?: "novo"


            // Se veio tarefa, popula os campos para edição/detalhes
            tarefa?.let {
                id = it.id
                editTextNome.setText(it.nome)
                editTextDescricao.setText(it.descricao)
                dataSelecionada = Calendar.getInstance().apply { timeInMillis = it.data }
                concluido = it.concluida
                prioridadeAlta = it.prioridadeAlta
                prioridadeMedia = it.prioridadeMedia
                prioridadeBaixa = it.prioridadeBaixa

                atualizarTextoData()
                atualizarTextoEstado()
                atualizarTextoPrioridade()
            }

            // Se for modo detalhes, desabilita edição e salvar
            if (acao == "detalhes") {
                editTextNome.isEnabled = false
                editTextDescricao.isEnabled = false
                buttonDatePicker.isEnabled = false
                buttonSalvar.isEnabled = false
                buttonConcluir.isEnabled = false
                buttonDeixarPendente.isEnabled = false
                buttonPrioridadeAlta.isEnabled = false
                buttonPrioridadeMedia.isEnabled = false
                buttonPrioridadeBaixa.isEnabled = false

            }


            // Função a ser chamada com o botão do datePicker
            buttonDatePicker.setOnClickListener {
                abrirDatePicker()
            }

            // Função a ser chamada com o botão de Salvar
            buttonSalvar.setOnClickListener {
                salvarTarefa()
            }
            // Função a ser chamada com o botão de Cancelar
            buttonCancelar.setOnClickListener {
                finish()
            }

            buttonConcluir.setOnClickListener{
                concluirTarefa()
            }

            buttonDeixarPendente.setOnClickListener(){
                deixarPendente()
            }

            buttonPrioridadeAlta.setOnClickListener(){
                deixarPrioridadeAlta()
            }

            buttonPrioridadeMedia.setOnClickListener(){
                deixarPrioridadeMedia()
            }

            buttonPrioridadeBaixa.setOnClickListener(){
                deixarPrioridadeBaixa()
            }
    }

    /*

    * Cria uma caixa de diálogo pro usuario escolher a data
    * Inicializa com a data atual ou com data já selecionada
    * Quando o usuário escolhe , a data é atualizada e o texto na tela também

     */
    private fun abrirDatePicker() {
        val calendar = dataSelecionada ?: Calendar.getInstance()
        val listener = DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
            calendar.set(Calendar.YEAR, ano)
            calendar.set(Calendar.MONTH, mes)
            calendar.set(Calendar.DAY_OF_MONTH, dia)
            dataSelecionada = calendar
            atualizarTextoData()
        }

        DatePickerDialog(
            this,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    /*
        * Formata a data no padrão Dia/Mês/Ano
        * Atualiza o Text View pra mostrar a data selecionada

     */
    private fun atualizarTextoData() {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        selectedDateText.text = "Data escolhida: ${formato.format(dataSelecionada?.time)}"
    }

    private fun atualizarTextoEstado(){
        if (concluido){
            selectedState.text = "Tarefa Concluida"
        }
        else{
            selectedState.text = "Tarefa Pendente"
        }
    }


    private fun atualizarTextoPrioridade(){

        if(prioridadeAlta){
            selectedPrioridade.text = "Baixa"
        }
        else if(prioridadeMedia){
            selectedPrioridade.text = "Media"
        }
        else{
            selectedPrioridade.text = "Alta"
        }
    }

    /*
        * Pega os valores digitados pelo usuário
        * Se algum campo (obrigatório) estiver vazio mostra erro e para
        * Se tudo estiver certo, mostra uma mensagem de sucesso
     */

    private fun salvarTarefa() {
        val nome = editTextNome.text.toString().trim()
        val descricao = editTextDescricao.text.toString().trim()
        val data = dataSelecionada?.time

        if (nome.isEmpty()) {
            editTextNome.error = "Informe o nome da tarefa"
            return
        }
        if (descricao.isEmpty()) {
            editTextDescricao.error = "Informe a descrição"
            return
        }
        if (data == null) {
            Toast.makeText(this, "Escolha uma data limite", Toast.LENGTH_SHORT).show()
            return
        }

        val tarefa = Tarefa(
            id = id,
            nome = nome,
            descricao = descricao,
            data = dataSelecionada?.timeInMillis ?: 0L,
            concluida = concluido,
            prioridadeAlta = prioridadeAlta,
            prioridadeMedia = prioridadeMedia,
            prioridadeBaixa = prioridadeBaixa

        )
        val intent = Intent()
        intent.putExtra("tarefa", tarefa)
        setResult(RESULT_OK, intent)
        finish()


        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun concluirTarefa(){
         this.concluido = true

         selectedState.text = "Tarefa Concluida"
    }

    private fun deixarPendente(){
        this.concluido = false

        selectedState.text = "Tarefa Pendente"
    }

    private fun deixarPrioridadeAlta(){

        this.prioridadeAlta = true
        this.prioridadeMedia = false
        this.prioridadeBaixa = false
        selectedPrioridade.text = "Alta"
    }

    private fun deixarPrioridadeMedia(){
        this.prioridadeMedia= true
        this.prioridadeAlta = false
        this.prioridadeBaixa = false
        selectedPrioridade.text = "Media"
    }

    private fun deixarPrioridadeBaixa(){
        this.prioridadeBaixa = false
        selectedPrioridade.text = "Baixa"
    }

}