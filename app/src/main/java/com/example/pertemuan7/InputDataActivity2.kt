package com.example.pertemuan7

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pertemuan7.databinding.ActivityInputData2Binding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class InputDataActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityInputData2Binding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputData2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Data tempat wisata untuk spinner
        val dataWisata = listOf("Baturraden")

        // Inisialisasi spinner dengan data tempat wisata
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataWisata)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spWisata.adapter = adapter

        // Tambahkan listener untuk menghitung total harga tiket saat jumlah pengunjung berubah
        binding.imageMinus1.setOnClickListener {
            decreaseJumlahAnak()
            hitungTotalHarga()
        }
        binding.imageAdd1.setOnClickListener {
            increaseJumlahAnak()
            hitungTotalHarga()
        }
        binding.imageMinus2.setOnClickListener {
            decreaseJumlahDewasa()
            hitungTotalHarga()
        }
        binding.imageAdd2.setOnClickListener {
            increaseJumlahDewasa()
            hitungTotalHarga()
        }

        // Panggil hitungTotalHarga saat jumlah pengunjung anak atau dewasa berubah melalui spinner
        binding.spWisata.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                hitungTotalHarga()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Inisialisasi Firebase Auth dan Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Tambahkan OnClickListener untuk memilih tanggal berangkat menggunakan MaterialDatePicker
        binding.inputTanggal.setOnClickListener {
            showDatePicker()
        }

        binding.btnCheckout.setOnClickListener {
            val nama = binding.inputNama.text.toString()
            val telepon = binding.inputTelepon.text.toString()
            val totalHarga = hitungTotalHarga()
            val tanggal = binding.inputTanggal.text.toString()
            if (nama.isEmpty() || telepon.isEmpty()||tanggal.isEmpty()||totalHarga.toString()=="0"){
                Toast.makeText(this, "Mohon lengkapi semua isian sebelum melanjutkan!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan data ke Firebase
            val currentUser = auth.currentUser
            currentUser?.uid?.let { userId ->
                val data = HashMap<String, Any>()
                data["nama"] = nama
                data["telepon"] = telepon
                data["tempatWisata"] = binding.spWisata.selectedItem.toString()
                data["tanggalBerangkat"] = binding.inputTanggal.text.toString()
                data["jumlahAnak"] = binding.tvJmlAnak.text.toString().toInt()
                data["jumlahDewasa"] = binding.tvJmlDewasa.text.toString().toInt()
                data["totalHarga"] = totalHarga

                database.child("users").child(userId).setValue(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Data berhasil disimpan di Firebase!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HistoryActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan data di Firebase!", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private var jumlahAnak = 0
    private fun decreaseJumlahAnak() {
        if (jumlahAnak > 0) {
            jumlahAnak--
            binding.tvJmlAnak.text = jumlahAnak.toString()
        }
    }

    private fun increaseJumlahAnak() {
        jumlahAnak++
        binding.tvJmlAnak.text = jumlahAnak.toString()
    }

    private var jumlahDewasa = 0
    private fun decreaseJumlahDewasa() {
        if (jumlahDewasa > 0) {
            jumlahDewasa--
            binding.tvJmlDewasa.text = jumlahDewasa.toString()
        }
    }

    private fun increaseJumlahDewasa() {
        jumlahDewasa++
        binding.tvJmlDewasa.text = jumlahDewasa.toString()
    }

    private val datePicker: MaterialDatePicker<Long> by lazy {
        MaterialDatePicker.Builder.datePicker().build()
    }

    private fun showDatePicker() {
        datePicker.addOnPositiveButtonClickListener {
            val selectedDate = Date(it ?: 0)

            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)
            binding.inputTanggal.setText(formattedDate)
            hitungTotalHarga()
        }
        datePicker.show(supportFragmentManager, datePicker.toString())
    }

    private fun hitungTotalHarga(): Int {
        val tempatWisata = binding.spWisata.selectedItem.toString()
        val jumlahWisatawanAnak = binding.tvJmlAnak.text.toString().toInt()
        val jumlahWisatawanDewasa = binding.tvJmlDewasa.text.toString().toInt()

        // Define ticket prices for each place
        val hargaWisataAnak: Int
        val hargaWisataDewasa: Int

        when (tempatWisata) {
            "Baturraden" -> {
                hargaWisataAnak = jumlahWisatawanAnak * 20000 // 20,000 for anak-anak at Wisata A
                hargaWisataDewasa = jumlahWisatawanDewasa * 25000 // 25,000 for dewasa at Wisata A
            }
            "Menara Teratai" -> {
                hargaWisataAnak = jumlahWisatawanAnak * 15000 // 15,000 for anak-anak at Wisata B
                hargaWisataDewasa = jumlahWisatawanDewasa * 30000 // 30,000 for dewasa at Wisata B
            }
            "Mas Kemambang" -> {
                hargaWisataAnak = jumlahWisatawanAnak * 5000 // 5,000 for anak-anak at Wisata C
                hargaWisataDewasa = jumlahWisatawanDewasa * 15000 // 15,000 for dewasa at Wisata C
            }
            else -> {
                // Default values if the selected place is not recognized
                hargaWisataAnak = 0
                hargaWisataDewasa = 0
            }
        }

        val totalHarga = hargaWisataAnak + hargaWisataDewasa
        binding.tvTotalHarga.text = "Total Harga Tiket: Rp. $totalHarga"
        return totalHarga
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onImageButtonClick(view: View){
        val intent = Intent(this,DetailActivity2::class.java)
        startActivity(intent)
        finish()
    }
}