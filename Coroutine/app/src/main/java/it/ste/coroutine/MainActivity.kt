package it.ste.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "Coroutines"
    }

    private lateinit var job : Job

    private lateinit var randTv : TextView
    private lateinit var toggleBtn : Button

    private var started = false
    private var wasStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        randTv = findViewById(R.id.randTv)
        toggleBtn = findViewById(R.id.toggleBtn)

        setAndStartAsyncEngine()
        setListeners()
    }

    override fun onStart() {
        super.onStart()

        if(wasStarted) {
            setAndStartAsyncEngine()
        }
    }

    override fun onPause() {
        super.onPause()

        wasStarted = started
        if(started) {
            stopEngine()
        }
    }

    private fun setAndStartAsyncEngine() {
        if(!started) {
            started = true
            toggleBtn.text = getString(R.string.stop)
            job = lifecycleScope.launch(Dispatchers.Default) {
                var nextRandInt: Int
                while (true) {
                    Log.d(TAG, "Running...")

                    nextRandInt = Random.nextInt(1..100)

                    withContext(Dispatchers.Main) {
                        randTv.text = nextRandInt.toString()
                    }

                    delay(500)
                }
            }
        }
    }

    private fun stopEngine() {
        toggleBtn.text = getString(R.string.start)
        job.cancel()
        started = false
    }

    private fun setListeners() {
        toggleBtn.setOnClickListener {
            if(started) stopEngine() else setAndStartAsyncEngine()
        }
    }
}