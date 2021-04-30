package it.ssynx.urldownloader

import android.app.DownloadManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import it.ssynx.urldownloader.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var dlManager : DownloadManager
    private lateinit var appName : String
    private lateinit var fmtDlStarted : String
    private val activeReqIds = mutableListOf<Long>()
    private val mtx = Mutex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dlManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        appName = getString(R.string.app_name)
        fmtDlStarted = getString(R.string.dl_started)
        binding.logTv.movementMethod = ScrollingMovementMethod()
        launchLogger()
        setBtnOnClickListener()
        setEtAfterTextChangedListeners()
    }

    private fun restoreViewStatesAndShowToast(f: String) {
        binding.urlEt.text.clear()
        binding.filenameEt.text.clear()
        binding.downloadBtn.isEnabled = false
        binding.filenameEt.isEnabled = false
        binding.urlEt.requestFocus()
        Toast.makeText(this, fmtDlStarted.format(f), Toast.LENGTH_SHORT).show()
    }

    private fun buildRequest(filename : String, uri: Uri) : DownloadManager.Request {
        val req = DownloadManager.Request(uri)
        req.setTitle(appName)
        req.setDescription(filename)
        req.setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        )
        req.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or
                    DownloadManager.Request.NETWORK_MOBILE
        )
        req.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS, filename
        )

        return req
    }

    private fun setBtnOnClickListener() {
        binding.downloadBtn.setOnClickListener {
            val filename = binding.filenameEt.text.toString()
            val url = Uri.parse(binding.urlEt.text.toString())
            val req : DownloadManager.Request

            try {
                req = buildRequest(filename, url)
            } catch(e: IllegalArgumentException) {
                Toast
                    .makeText(this, getString(R.string.check_error), Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }

            activeReqIds.add(dlManager.enqueue(req))
            if(mtx.isLocked) {
                mtx.unlock()
            }

            restoreViewStatesAndShowToast(filename)
        }
    }

    private fun setEtAfterTextChangedListeners() {
        binding.urlEt.doAfterTextChanged {
            binding.filenameEt.isEnabled = binding.urlEt.text.isNotBlank()
            if(binding.filenameEt.text.isNotBlank()) {
                binding.downloadBtn.isEnabled = binding.filenameEt.isEnabled
            }
        }

        binding.filenameEt.doAfterTextChanged {
            binding.downloadBtn.isEnabled = binding.filenameEt.text.isNotBlank()
        }
    }

    private fun logBasedOnStatus(c: Cursor, idx: Int, status: Int) {
        when(status) {
            DownloadManager.STATUS_SUCCESSFUL -> {
                DownloadLog.successful(c, binding.logTv, this)
                activeReqIds.removeAt(idx)
            }

            DownloadManager.STATUS_FAILED -> {
                DownloadLog.failed(c, binding.logTv, this)
                activeReqIds.removeAt(idx)
            }

            DownloadManager.STATUS_PAUSED -> {
                DownloadLog.paused(c, binding.logTv, this)
            }

            DownloadManager.STATUS_PENDING -> {
                DownloadLog.pending(c, binding.logTv, this)
            }

            DownloadManager.STATUS_RUNNING -> {
                DownloadLog.running(c, binding.logTv, this)
            }
        }
    }

    private fun launchLogger() {
        lifecycleScope.launch(Dispatchers.Default) {
            mtx.lock()
            while(true) {
                mtx.lock()
                while(activeReqIds.size > 0) {
                    val reqIdx = (0 until activeReqIds.size).random()

                    withContext(Dispatchers.IO) {
                        val query = dlManager.query(
                            DownloadManager.Query().setFilterById(activeReqIds[reqIdx]))
                        val columnStatusIdx = query.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        query.moveToFirst()

                        logBasedOnStatus(query, reqIdx, query.getInt(columnStatusIdx))
                    }

                    delay(500)
                }
            }
        }
    }
}