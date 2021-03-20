package com.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private const val THIRD_ACTIVITY_RESCODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.simpleToastBtn).setOnClickListener {
            Toast.makeText(
                this, getString(R.string.simple_toast), Toast.LENGTH_LONG).show()
        }

        findViewById<Button>(R.id.anotherActivityBtn).setOnClickListener {
            val intent = Intent(this, SimpleActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.anotherActivityWithResultBtn).setOnClickListener {
            val intent = Intent(this, ComplexActivity::class.java)
            intent.putExtra("message", "You're being called")
            startActivityForResult(intent, THIRD_ACTIVITY_RESCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == THIRD_ACTIVITY_RESCODE) {
            val msg: String? =
                if (resultCode == 0) getString(R.string.no_ans_from_called_activity)
                else data?.getStringExtra("my_message")
            Toast
                .makeText(this, msg, Toast.LENGTH_LONG)
                .show()
        }
    }
}