package com.vasilevskii.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.vasilevskii.chat.ConstValue
import com.vasilevskii.chat.adapter.message.ReceiveViewHolder
import com.vasilevskii.chat.adapter.message.SentViewHolder
import com.vasilevskii.chat.databinding.ReceiveMessageBinding
import com.vasilevskii.chat.databinding.SentMessageBinding
import com.vasilevskii.chat.model.Message

class ChatAdapter(private val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val bindingReceiveMessage: ReceiveMessageBinding = ReceiveMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiveViewHolder(bindingReceiveMessage)
        } else {
            val bindingSentMessage: SentMessageBinding = SentMessageBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            SentViewHolder(bindingSentMessage)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java) {

            holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message

        } else {
            holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            ConstValue.ITEM_SENT
        } else {
            ConstValue.ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}