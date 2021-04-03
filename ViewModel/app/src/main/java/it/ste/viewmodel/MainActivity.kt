package it.ste.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import it.ste.viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val hashViewModel : HashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hashViewModel.hashes.observe(this, HashesObserver())
        binding.clearTextEt.addTextChangedListener(ClearTextChangedListener())
    }

    inner class ClearTextChangedListener : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}

        override fun afterTextChanged(s: Editable?) {}

        override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
            hashViewModel.doHash(s.toString())
        }
    }

    inner class HashesObserver : Observer<HashModel> {
        override fun onChanged(hashModel: HashModel?) {
            binding.hashMd5Tv.text = hashModel!!.md5
            binding.hashSha1Tv.text = hashModel.sha1
        }
    }
}