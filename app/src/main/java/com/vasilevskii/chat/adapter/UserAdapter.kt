package com.vasilevskii.chat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.vasilevskii.chat.ChatActivity
import com.vasilevskii.chat.ConstValue
import com.vasilevskii.chat.R
import com.vasilevskii.chat.databinding.UserLayoutBinding
import com.vasilevskii.chat.model.User

class UserAdapter(private val context: Context, private val userList: ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    inner class UserViewHolder(val binding: UserLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]

        holder.binding.textName.text = currentUser.name

        holder.itemView.setOnClickListener {

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ConstValue.EXTRA_NAME, currentUser.name)
            intent.putExtra(ConstValue.EXTRA_UID, currentUser.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}