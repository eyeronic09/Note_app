package com.example.noteapp.HomeScreen.data_layer.remote.networkmonitor

import kotlinx.coroutines.flow.Flow

interface networkMonitor {
    val isConnected: Flow<Boolean>
}