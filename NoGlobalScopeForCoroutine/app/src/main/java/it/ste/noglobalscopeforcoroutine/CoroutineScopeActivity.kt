package it.ste.noglobalscopeforcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.*

class CoroutineScopeActivity : AppCompatActivity() {
    //change scope behaviour based on your needs
    //                          vvvvvvvvvvvvvvvvvv DEFAULT DISPATCHER FOR THIS SCOPE
    val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        scope.launch {
            while(true) {
                delay(1000)
                Log.d("CoroutineScopesApp", "CoroutineScoped (onCreate) **1** coroutine running...")
            }
        }

        scope.launch {
            while(true) {
                delay(1000)
                Log.d("CoroutineScopesApp", "CoroutineScoped (onCreate) **2** coroutine running...")
            }
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel() //cancel every coroutine in this scope
        Log.d("CoroutineScopesApp", "CoroutineScopeActivity **DESTROYED**")
    }
}