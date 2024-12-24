package com.example.filemanagement

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var currentDirectory: File
    private lateinit var fileList: Array<out File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        currentDirectory = File(getFilesDir(), "Test")

        // Ensure the "Test" folder exists and log the result
        if (!currentDirectory.exists()) {
            Log.d("FileManager", "Creating 'Test' directory")
            currentDirectory.mkdirs()
        } else {
            Log.d("FileManager", "'Test' directory already exists")
        }

        loadFiles(currentDirectory)
    }

    private fun loadFiles(directory: File) {
        fileList = directory.listFiles() ?: emptyArray()
        Log.d("FileManager", "Loaded files: ${fileList.joinToString { it.name }}")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileList.map { it.name })
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedFile = fileList[position]
            if (selectedFile.isDirectory) {
                Log.d("FileManager", "Selected directory: ${selectedFile.name}")
                loadFiles(selectedFile)
            } else {
                if (selectedFile.extension == "txt") {
                    Log.d("FileManager", "Selected file: ${selectedFile.name}")
                    val intent = Intent(this, FileContentActivity::class.java)
                    intent.putExtra("file_path", selectedFile.absolutePath)
                    startActivity(intent)
                }
            }
        }
    }
}