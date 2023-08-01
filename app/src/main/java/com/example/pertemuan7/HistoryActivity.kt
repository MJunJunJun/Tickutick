package com.example.pertemuan7

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pertemuan7.databinding.ActivityHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = FirebaseAuth.getInstance().currentUser!!

        adapter = HistoryAdapter(ArrayList())
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        databaseRef = FirebaseDatabase.getInstance().getReference("users")
            //.child(FirebaseAuth.getInstance().currentUser!!.uid.toString())

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val historyList = ArrayList<History>()
                for (snapshot in dataSnapshot.children) {
                    val users = snapshot.getValue(History::class.java)
                    users?.let { historyList.add(it) }
                }
                adapter.setData(historyList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    fun onImageButtonClick(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
