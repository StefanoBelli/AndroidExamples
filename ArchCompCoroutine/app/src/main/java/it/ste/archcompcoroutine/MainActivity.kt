package it.ste.archcompcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import it.ste.archcompcoroutine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : FileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDataObserver()
        setUiListeners()
    }

    private fun setDataObserver() {
        viewModel.data.observe(this, {
            binding.urlEt.isEnabled = true
            binding.httpGetBtn.isEnabled = true
            binding.urlEt.requestFocus()
            binding.urlEt.selectAll()
            if(it.code == -1) {
                binding.fileContentTv.text = it.message
            } else {
                binding.fileContentTv.text =
                    "HTTP response code: ${it.code}\n" +
                            "HTTP response message: ${it.message}\n" +
                            "Filename: ${it.name}\n**********\n\n${it.content}"
            }
        })
    }

    private fun setUiListeners() {
        binding.urlEt.doAfterTextChanged {
            binding.httpGetBtn.isEnabled = it!!.isNotBlank()
        }

        binding.httpGetBtn.setOnClickListener {
            binding.fileContentTv.text = ""
            var url = binding.urlEt.text.toString()
            if(!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
                binding.fileContentTv.text = "NOTE: RealUrl = $url\n"
            }
            viewModel.fetch(url)
            binding.httpGetBtn.isEnabled = false
            binding.urlEt.isEnabled = false
            binding.fileContentTv.text = "${binding.fileContentTv.text}Wait..."
        }
    }
}