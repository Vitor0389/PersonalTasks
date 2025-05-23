Aplicativo Android para gerenciamento de tarefas pessoais, desenvolvido com Kotlin, Room e RecyclerView.

📱 Funcionalidades
Cadastrar, editar, excluir e visualizar detalhes de tarefas.

Seleção de data via DatePickerDialog.

Persistência local com banco de dados Room.

Listagem dinâmica com RecyclerView e Adapter.

Navegação com Intent e ActivityResultLauncher.

🏗️ Tecnologias e Bibliotecas

Kotlin

Android Jetpack (Room, Lifecycle, ViewModel)

RecyclerView

Parcelable

Coroutines

📦 Estrutura do Projeto

├── model
│   ├── AppDatabase.kt
│   ├── Converters.kt
│   ├── Tarefa.kt
│   └── TarefaDao.kt
├── controller
│   └── TarefaController.kt
├── view
│   ├── MainActivity.kt
│   ├── CadastroActivity.kt
│   └── TarefaAdapter.kt

🚀 Como Executar
Clone o repositório.

Abra no Android Studio.

Execute em um emulador ou dispositivo físico.

✅ Requisitos
Android Studio LadyBug ou superior.

SDK mínimo: 21 (Lollipop).
