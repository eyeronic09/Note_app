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
import com.example.noteapp.HomeScreen.domain_layer.Use_Case.UpdateNotesUseCase
import com.example.noteapp.HomeScreen.domain_layer.repository.NoteRepository
import com.example.noteapp.TodoFeature.AddScreen.TodoAddScreenVM
import com.example.noteapp.TodoFeature.HomeScreen.UiLayer.TodoHomeScreenVM
import com.example.noteapp.TodoFeature.HomeScreen.data.Repository.TodoRepositoryImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSources
import com.example.noteapp.TodoFeature.HomeScreen.data.local.DataSource.LocalDataSourcesImpl
import com.example.noteapp.TodoFeature.HomeScreen.data.local.Database.TodoDataBase
import com.example.noteapp.TodoFeature.HomeScreen.domain.repository.TodoRepository
import com.example.noteapp.TodoFeature.Todo_Notification.NotificationConstants
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
                NotificationConstants.CHANNEL_ID,
                NotificationConstants.CHANNEL_NAME,
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
        factory { AddNoteUseCase(get()) }
        factory { UpdateNotesUseCase(get()) }
        factory { DeleteNoteUseCase(get()) }

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

        // Repository
        single<TodoRepository> {
            TodoRepositoryImpl(get())
        }
        

        viewModel {
            TodoHomeScreenVM(get() , )
        }
        viewModel {
            TodoAddScreenVM(get(),get())
        }
    }

}