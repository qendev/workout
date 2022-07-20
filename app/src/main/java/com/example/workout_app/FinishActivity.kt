package com.example.workout_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.workout_app.databinding.ActivityFinishBinding
import com.example.workout_app.room.dao.HistoryDao
import com.example.workout_app.room.entity.HistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var binding:ActivityFinishBinding? = null

    //inorder to implement text to speech
    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        binding= ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        tts = TextToSpeech(this, this)




        setSupportActionBar(binding?.toolbarFinish)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //set the backButton
        binding?.toolbarFinish?.setNavigationOnClickListener {
            onBackPressed()
        }



        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        speakOut(binding?.tvMessage?.text.toString())

        //setup the dao inorder to call it
        val historyDao =(application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)


    }

    private fun addDateToDatabase(historyDao: HistoryDao){
        //prepare the date before storing it
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date:","" + dateTime)
        val sdf = SimpleDateFormat("dd MMM yyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date:","" + dateTime)

        //add the date to the database in the bckground
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                historyDao.insert(HistoryEntity(date))
                Log.e("Date","Added...")
            }
        }


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts?.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }



    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts!=null){
            tts?.stop()
            tts?.shutdown()
        }
    }
}