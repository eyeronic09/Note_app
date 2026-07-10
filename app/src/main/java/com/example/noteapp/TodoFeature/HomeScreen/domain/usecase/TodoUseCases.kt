package com.example.noteapp.TodoFeature.HomeScreen.domain.usecase

data class TodoUseCases(
    val getAllTodosUseCase: GetAllTodosUseCase,
    val addTodoUseCase: AddTodoUseCase,
    val deleteTodoUseCase: DeleteTodoUseCase,
    val updateTodoUseCase: UpdateTodoUseCase,
    val getTodosByDateUseCase: GetTodosByDateUseCase,
    val getTodoByIdUseCase: GetTodoByIdUseCase
)
