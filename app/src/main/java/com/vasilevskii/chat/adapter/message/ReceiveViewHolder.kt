package com.vasilevskii.chat.adapter.message

import androidx.recyclerview.widget.RecyclerView
import com.vasilevskii.chat.databinding.ReceiveMessageBinding

class ReceiveViewHolder(bindingReceiveMessage: ReceiveMessageBinding) : RecyclerView.ViewHolder(bindingReceiveMessage.root) {

    val receiveMessage = bindingReceiveMessage.textReceiveMessage
}