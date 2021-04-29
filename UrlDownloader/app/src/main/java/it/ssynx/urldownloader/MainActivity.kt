package it.ssynx.urldownloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import it.ssynx.urldownloader.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        const val SAVE_FILENAME = "index.html"
    }

    private lateinit var binding : ActivityMainBinding
    private lateinit var dlManager : DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

        dlManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private fun setListeners() {
        binding.fireBtn.setOnClickListener {
            val req = DownloadManager.Request(Uri.parse(binding.urlEt.text.toString()))
            req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, SAVE_FILENAME)
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or
                    DownloadManager.Request.NETWORK_WIFI)
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            dlManager.enqueue(req)
            binding.urlEt.text.clear()
        }

        binding.urlEt.doAfterTextChanged {
            binding.fireBtn.isEnabled = it!!.isNotBlank()
        }
    }
}