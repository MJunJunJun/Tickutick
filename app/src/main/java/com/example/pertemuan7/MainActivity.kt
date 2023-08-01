package com.example.pertemuan7

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan7.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTiketBTR.setOnClickListener {
            val intent = Intent(this,DetailActivity2::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnTiketMTR.setOnClickListener {
            val intent = Intent(this,DetailActivity3::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnTiketMKB.setOnClickListener {
            val intent = Intent(this,DetailActivity4::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageProfile.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}