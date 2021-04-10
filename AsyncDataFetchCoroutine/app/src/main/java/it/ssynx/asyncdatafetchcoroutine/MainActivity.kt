package it.ssynx.asyncdatafetchcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TEST_URL = "https://google.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        Blocking I/O <=> Dispatchers.IO
         */
        lifecycleScope.launch(Dispatchers.IO) {
            val url = URL(TEST_URL)
            var msg = ""
            with(url.openConnection() as HttpURLConnection) {
                msg = "$TEST_URL says: \"$responseCode $responseMessage\""
                inputStream.bufferedReader().use { it.readText() }
            }

            /*
            Coroutine is switching context to UI single thread continue with this coroutine
            (Main coroutine dispatcher has only one thread to run them all, be fast)
             */
            withContext(Dispatchers.Main) {
                Toast
                        .makeText(this@MainActivity, msg, Toast.LENGTH_LONG)
                        .show()
            }
        }
    }
}