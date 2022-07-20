package com.example.workout_app.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workout_app.room.dao.HistoryDao
import com.example.workout_app.room.entity.HistoryEntity


@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryDatabase:RoomDatabase() {

    //connect the database to the dao
    abstract fun historyDao():HistoryDao

    //add a companion object that allows us to add functions to the employee database class

    companion object{

        @Volatile
        private  var INSTANCE:HistoryDatabase? = null

        //create a helper function to get a database
        fun getInstance(context: Context):HistoryDatabase{
            //to make sure only one thread may enter a synchronized block at a time
            synchronized(this){
                var instance = INSTANCE

                if (instance== null){
                    instance = Room.databaseBuilder(context.applicationContext, HistoryDatabase::class.java,
                        "history_database")
//                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return  instance

            }
        }
    }
}