package com.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ComplexActivity : AppCompatActivity() {

    private lateinit var messageEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complex)

        val msg = intent.getStringExtra("message")
        Toast
            .makeText(
            this,
                "${getString(R.string.msg_calling_activity)}: $msg",
                Toast.LENGTH_SHORT)
            .show()

        findViewById<Button>(R.id.goBackBtn).setOnClickListener {
            setMsgResult()
            finish()
        }

        messageEditText = findViewById(R.id.msgEditText)
    }

    override fun onBackPressed() {
        setMsgResult()
        super.onBackPressed()
    }

    private fun setMsgResult() {
        val res = messageEditText.text.isNotBlank()
        setResult(if (res) 1 else 0, Intent().apply {
            putExtra("my_message", messageEditText.text.toString())
        })
    }
}