package com.example.kotlinproject.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinproject.Models.ModelUser
import com.example.kotlinproject.R

class Adapter(private val  userList: List<ModelUser>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_name: TextView = itemView.findViewById(R.id.text_name)
        var item_date: TextView = itemView.findViewById(R.id.dueDate_month)
        var lay_item : RelativeLayout = itemView.findViewById(R.id.layout_item)

    }
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.item_name.text = user.title
        holder.item_date.text = user.Date.toString()
        holder.lay_item.setOnClickListener(object :OnClickListener{
            override fun onClick(v: View?){
                Toast.makeText(holder.lay_item.context, "setting", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}