package com.example.pertemuan7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan7.databinding.ListItemHistoryBinding


class HistoryAdapter(private val dataMhs : ArrayList<History>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataMhs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jumlahAnakStr = "Jumlah Anak: " + dataMhs[position].jumlahAnak.toString()
        holder.binding.tvJumlahAnak.text = jumlahAnakStr

        val jumlahDewasaStr = "Jumlah Dewasa: " + dataMhs[position].jumlahDewasa.toString()
        holder.binding.tvJumlahDewasa.text = jumlahDewasaStr

        val jumlahnamaStr = "Nama: " + dataMhs[position].nama.toString()
        holder.binding.tvNama.text = jumlahnamaStr

        val jumlahtglStr = "Tanggal Keberangkatan: " + dataMhs[position].tanggalBerangkat.toString()
        holder.binding.tvTanggalBerangkat.text = jumlahtglStr

        val jumlahteleponStr = "Telepon: " + dataMhs[position].telepon.toString()
        holder.binding.tvTelepon.text = jumlahteleponStr

        val jumlahwisataStr = "Tempat Wisata: " + dataMhs[position].tempatWisata.toString()
        holder.binding.tvTempatWisata.text = jumlahwisataStr

        val jumlahhargaStr = "Total Harga: " + dataMhs[position].totalHarga.toString()
        holder.binding.tvTotalHarga.text = jumlahhargaStr
    }

    fun setData(newData: ArrayList<History>) {
        dataMhs.clear()
        dataMhs.addAll(newData)
        notifyDataSetChanged()
    }}