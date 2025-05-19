package com.personaltasks.controller
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.personaltasks.R
import java.util.Locale
import java.util.UUID

class CadastroActivity : AppCompatActivity() {

    // Referência dos componentes
    private lateinit var editTextNome: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var selectedDateText: TextView
    private lateinit var buttonDatePicker: Button
    private lateinit var buttonSalvar: Button
    private lateinit var buttonCancelar: Button

    private var dataSelecionada: Calendar? = null




    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_cadastro);




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

}