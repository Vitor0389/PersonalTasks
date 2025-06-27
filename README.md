Aplicativo Android para gerenciamento de tarefas pessoais, desenvolvido com Kotlin, Firebase Realtime Database e RecyclerView.

📱 Funcionalidades
Cadastrar, editar, excluir (exclusão lógica) e visualizar detalhes de tarefas.

Reativar tarefas excluídas.

Seleção de data via DatePickerDialog.

Persistência de dados no Firebase Realtime Database, permitindo sincronização remota.

Listagem dinâmica com RecyclerView e Adapter.

Navegação entre telas com Intent e ActivityResultLauncher.

Menu de contexto (Context Menu) com opções para editar, excluir, visualizar detalhes e reativar tarefas.

🏗️ Tecnologias e Bibliotecas
Kotlin

Firebase Realtime Database

Android Jetpack (Lifecycle, ViewModel)

RecyclerView

Parcelable

Coroutines

🚀 Como Executar
Clone o repositório.

Abra o projeto no Android Studio.

Configure seu projeto Firebase e conecte ao app (incluir o google-services.json na pasta app/).

Certifique-se de habilitar o Realtime Database no Firebase e ajustar as regras para testes, por exemplo:

{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
Execute o app em um emulador ou dispositivo físico com internet.

✅ Requisitos
Android Studio Bumblebee ou superior.

SDK mínimo: 21 (Lollipop).

Conta Firebase configurada.

🎥 Demonstração
Link para vídeo de execução do app:
https://drive.google.com/file/d/1ckHBY1nK-3SEDOBjOd9_MI9YKJUNTbK3/view?usp=sharing
