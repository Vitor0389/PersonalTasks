package com.personaltasks.controller
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.personaltasks.R
import java.util.UUID

class CadastroActivity : AppCompatActivity() {
    private lateinit var editTextNome: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var textDataEscolhida: TextView
    private lateinit var buttonDatePicker: Button
    private lateinit var buttonSalvar: Button
    private lateinit var buttonCancelar: Button

    private var dataSelecionada: Calendar? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro);

        // Referências dos componentes
        editTextNome = findViewById(R.id.editText1)
        editTextDescricao = findViewById(R.id.editText2)
        val selectedDateText: TextView = findViewById(R.id.selectedDateText)
        //selectedDateText : TextView = findViewById(R.id.selectedDateText)
        buttonDatePicker = findViewById(R.id.button_date_picker)
        buttonSalvar = findViewById(R.id.button2)
        buttonCancelar = findViewById(R.id.button3)
    }
}