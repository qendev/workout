package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workout_app.databinding.ActivityHistoryBinding
import com.example.workout_app.room.dao.HistoryDao
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding:ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistory)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }

        //set the backButton
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        //create a dao object inorder to be able to pass it
        val historyDao = (application as WorkOutApp).db.historyDao()
        getAllCompleteDates(historyDao)
    }
    //this method will be useful for retrieving data from the database
    private fun getAllCompleteDates(historyDao: HistoryDao){
        //do it in the background
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{ alCompletedDatesList ->
                if (alCompletedDatesList.isNotEmpty()){
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.INVISIBLE

                    //setup the history recyclerView with a layout and an adapter
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    //setup the list and fill it with data
                    //we get the data from the database
                    val dates = ArrayList<String>()
                    for (date in alCompletedDatesList){
                        dates.add(date.dateHistory)
                    }
                    //parse the data inside of the adapter
                    val historyAdapter =HistoryAdapter(dates)

                    //set the recyclerView to the adapter
                    binding?.rvHistory?.adapter = historyAdapter

                }
                else{
                    binding?.tvHistory?.visibility = View.GONE
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE


                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}