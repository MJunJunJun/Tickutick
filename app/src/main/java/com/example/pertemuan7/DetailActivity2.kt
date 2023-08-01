package com.example.pertemuan7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.pertemuan7.databinding.ActivityDetail2Binding

class DetailActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityDetail2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

    binding.button2.setOnClickListener {
        val intent = Intent(this,InputDataActivity2::class.java)
        startActivity(intent)
        finish()
    }
    }

    fun onImageButtonClick(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}