package com.vasilevskii.chat.adapter.message

import androidx.recyclerview.widget.RecyclerView
import com.vasilevskii.chat.databinding.SentMessageBinding

class SentViewHolder(bindingSentMessage: SentMessageBinding) : RecyclerView.ViewHolder(bindingSentMessage.root) {

    val sentMessage = bindingSentMessage.textSentMessage
}