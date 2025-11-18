package com.example.noteapp.HomeScreen.Di

import android.app.Application
import androidx.room.Room
import com.example.noteapp.HomeScreen.data_layer.local.Dao.NotesDao
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSources
import com.example.noteapp.HomeScreen.data_layer.local.Datasource.NotesLocalDataSourcesImpl
import com.example.noteapp.HomeScreen.data_layer.local.database.Note_db
import com.example.noteapp.HomeScreen.data_layer.local.database.NotesDatabase
import com.example.noteapp.HomeScreen.data_layer.repository.RepositoryImpl
import com.example.noteapp.HomeScreen.domain_layer.repository.Repository
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.module


class AppModule () : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AppModule)
            modules(appModule)
        }
    }

    private val appModule = module {
        // Database
        single {
            Room.databaseBuilder(
                androidApplication(),
                Note_db::class.java,
                "Notes_DB"
            ).build()
        }
        
        // DAO
        single {
            get<Note_db>().notesDao()
        }
        
        // Data Source
        single<NotesLocalDataSources> {
            NotesLocalDataSourcesImpl(get())
        }
        
        // Repository
        single<Repository> {
            RepositoryImpl(get())
        }
    }


}