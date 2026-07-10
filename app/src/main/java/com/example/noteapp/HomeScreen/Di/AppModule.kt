package com.example.noteapp.HomeScreen.Di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room.databaseBuilder
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSourcesImpl
import com.example.noteapp.HomeScreen.data_layer.local.database.NoteRoomDatabase
import com.example.noteapp.HomeScreen.data_layer.repository.RepositoryImpl
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.AddNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.DeleteNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.GetAllNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.GetNoteByIdUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.NoteUseCases
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.UpdateNotesUseCase
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.TodoFeature.AddScreen.TodoAddScreenVM
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenVM
import com.example.noteapp.TodoFeature.HomeScreen.data.Repository.TodoRepositoryImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSources
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSourcesImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Database.TodoDataBase
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.AddTodoUseCase
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.DeleteTodoUseCase
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.GetAllTodosUseCase
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.GetTodoByIdUseCase
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.GetTodosByDateUseCase
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.TodoUseCases
import com.example.noteapp.TodoFeature.HomeScreen.domain.usecase.UpdateTodoUseCase
import com.example.noteapp.TodoFeature.Todo_Notification.NotificationDataSource.NotificationActions
import com.example.noteapp.TodoFeature.Todo_Notification.Scheduler.NotificationScheduler
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


class AppModule () : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {

        super.onCreate()
        createNotificationChannel()
        startKoin {
            androidLogger()
            androidContext(this@AppModule)
            modules(noteModule , todoModule)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "todo_channel",
                "Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private val noteModule = module {
        // Database
        single {
            databaseBuilder(
                androidApplication(),
                NoteRoomDatabase::class.java,
                "Notes_DB"
            )
                .fallbackToDestructiveMigration(true)
                .build()
        }
        
        // DAO
        single {
            get<NoteRoomDatabase>().notesDao()
        }
        
        // Data Source
        single<NotesLocalDataSources> {
            NotesLocalDataSourcesImpl(get())
        }
        
        // Repository
        single<NoteRepository> {
            RepositoryImpl(get())
        }


        //UseCase
        factory { GetAllNoteUseCase(get()) }
        factory { AddNoteUseCase(
            repository = get(),

        ) }
        factory { UpdateNotesUseCase(get()) }
        factory { DeleteNoteUseCase(get()) }
        factory { GetNoteByIdUseCase(get()) }

        factory {
            NoteUseCases(
                getAllNoteUseCase = get(),
                deleteNoteUseCase = get(),
                addNoteUseCase = get(),
                updateNotesUseCase = get(),
                getNoteByIdUseCase = get()
            )
        }

        viewModel {
            HomeScreenViewModel(get())
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val todoModule = module {
        single {
            databaseBuilder(
                context = androidApplication(),
                klass = TodoDataBase::class.java,
                name = "Todo_DB"
            ).fallbackToDestructiveMigration(true).build()
        }
        single {
            get<TodoDataBase>().TodoDao()
        }
        single<LocalDataSources> {
            LocalDataSourcesImpl(get())
        }
        single<NotificationActions> { NotificationScheduler(androidContext()) }

        // Repository
        single<TodoRepository> {
            TodoRepositoryImpl(get())
        }

        // UseCase
        factory { GetAllTodosUseCase(get()) }
        factory { AddTodoUseCase(get() , get()) }
        factory { DeleteTodoUseCase(get()) }
        factory { UpdateTodoUseCase(get()) }
        factory { GetTodosByDateUseCase(get()) }
        factory { GetTodoByIdUseCase(get()) }

        factory {
            TodoUseCases(
                getAllTodosUseCase = get(),
                addTodoUseCase = get(),
                deleteTodoUseCase = get(),
                updateTodoUseCase = get(),
                getTodosByDateUseCase = get(),
                getTodoByIdUseCase = get()
            )
        }


        viewModel {
            TodoHomeScreenVM(get())
        }
        viewModel {
            TodoAddScreenVM(get(), androidApplication())
        }


    }

}