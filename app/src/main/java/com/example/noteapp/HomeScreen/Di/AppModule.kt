package com.example.noteapp.HomeScreen.Di

import android.app.Application
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.noteapp.HomeScreen.Ui_prestentionLayer.Home.HomeScreenViewModel
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSourcesImpl
import com.example.noteapp.HomeScreen.data_layer.local.database.NoteRoomDatabase
import com.example.noteapp.HomeScreen.data_layer.repository.RepositoryImpl
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.AddNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.DeleteNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.GetAllNoteUseCase
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.UpdateNotesUseCase
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.HomeScreenVM
import com.example.noteapp.TodoFeature.HomeScreen.data.Repository.TodoRepositoryImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSources
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSourcesImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Database.TodoDataBase
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


class AppModule () : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppModule)
            modules(noteModule , todoModule)
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
        factory { AddNoteUseCase(get()) }
        factory { UpdateNotesUseCase(get()) }
        factory { DeleteNoteUseCase(get()) }

        viewModel {
            HomeScreenViewModel(get())
        }



    }

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

        // Repository
        single<TodoRepository> {
            TodoRepositoryImpl(get())
        }
        viewModel {
            HomeScreenVM(get())
        }
    }

}