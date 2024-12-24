package com.example.filemanagement

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        val filePath = intent.getStringExtra("file_path") ?: ""
        val file = File(filePath)
        val fileContent = file.readText()

        val tvFileContent = findViewById<TextView>(R.id.tvFileContent)
        tvFileContent.text = fileContent
    }
}