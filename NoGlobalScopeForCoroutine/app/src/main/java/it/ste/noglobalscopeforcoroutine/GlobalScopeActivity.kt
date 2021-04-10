package it.ste.noglobalscopeforcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlobalScopeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        GlobalScope.launch(Dispatchers.Default) {
            while(true) {
                delay(1000)
                Log.d("CoroutineScopesApp", "GlobalScoped coroutine running...")
            }
        }

        findViewById<Button>(R.id.button4).setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CoroutineScopesApp", "GlobalScopeActivity **DESTROYED**")
    }
}