package com.example.workout_app

import android.app.Application
import com.example.workout_app.room.database.HistoryDatabase

class WorkOutApp:Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}