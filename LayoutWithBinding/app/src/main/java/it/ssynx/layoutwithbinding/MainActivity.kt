package it.ssynx.layoutwithbinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import it.ssynx.layoutwithbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var pushed : String? = null
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clearBtn.setOnClickListener {
            binding.strEt.text.clear()
        }

        binding.pushBtn.setOnClickListener {
            if(!binding.strEt.text.isEmpty()) {
                pushed = binding.strEt.text.toString()
                binding.pushedTv.text = pushed
                binding.pushedTv.visibility = View.VISIBLE
                binding.strEt.text.clear()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("pushed", pushed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        pushed = savedInstanceState.getString("pushed")
        if(pushed != null) {
            binding.pushedTv.visibility = View.VISIBLE
            binding.pushedTv.text = pushed
        }
    }
}