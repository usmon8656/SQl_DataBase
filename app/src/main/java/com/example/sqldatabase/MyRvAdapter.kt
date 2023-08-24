package com.example.sqldatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sqldatabase.databinding.ItemRvBindingBinding

class MyRvAdapter(val list: ArrayList<Contact> , val rvAction:RvAction):RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(val rvItem:ItemRvBindingBinding):ViewHolder(rvItem.root){
        fun onBind(contact: Contact){
            rvItem.txtName.text = contact.name
            rvItem.txtNumber.text = contact.number
            rvItem.btnDelet.setOnClickListener {
                rvAction.rvEditClick(contact)
            }
            rvItem.btnUpdate.setOnClickListener {
                rvAction.rvDeleteClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBindingBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface RvAction{
        fun rvEditClick(contact: Contact)
        fun rvDeleteClick(contact: Contact)
    }

}