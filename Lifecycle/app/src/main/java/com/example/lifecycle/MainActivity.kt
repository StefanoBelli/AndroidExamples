package com.example.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LifecycleExampleApp"
    }

    private lateinit var counterTextView : TextView
    private var counter = 0

    private fun setCounterText(cnt: Int) {
        counterTextView.text = String.format(cnt.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter")
        }

        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counterTextView)
        setCounterText(counter)

        val btn = findViewById<Button>(R.id.incButton)
        btn.setOnClickListener{ setCounterText(++counter) }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("counter", counter);
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }
}