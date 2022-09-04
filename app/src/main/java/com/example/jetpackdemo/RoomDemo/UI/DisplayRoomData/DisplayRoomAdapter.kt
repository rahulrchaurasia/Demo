package com.example.jetpackdemo.RoomDemo.UI.DisplayRoomData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.RoomDemo.Entity.Contact

import com.example.jetpackdemo.databinding.RoomDisplayItemsBinding

class DisplayRoomAdapter(private var listContact: List<Contact>) :
    RecyclerView.Adapter<DisplayRoomAdapter.DisplayRoomHolder>() {


    private lateinit var binding: RoomDisplayItemsBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplayRoomHolder {

        binding = RoomDisplayItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DisplayRoomHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DisplayRoomHolder, position: Int) {

        binding.apply {
            txtName.text = listContact[position].name
            txtMob.text = listContact[position].mobile
            txtAddress.text = listContact[position].address
            txtID.text = "ID: " + listContact[position].id.toString()
        }


    }

    override fun getItemCount() = listContact.size


    class DisplayRoomHolder(itemView: View) : RecyclerView.ViewHolder(itemView ){


    }

    fun setData(listContact: List<Contact>){

        this.listContact = listContact
        notifyDataSetChanged()

    }
}