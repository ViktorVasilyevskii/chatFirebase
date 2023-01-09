package com.vasilevskii.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.vasilevskii.chat.adapter.ChatAdapter
import com.vasilevskii.chat.databinding.ActivityChatBinding
import com.vasilevskii.chat.model.Message

class ChatActivity : AppCompatActivity() {

    private lateinit var bindingChatActivity: ActivityChatBinding
    private lateinit var mDatabaseReference: DatabaseReference

    private var receiverRoom: String? = null
    private var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingChatActivity = ActivityChatBinding.inflate(layoutInflater)
        setContentView(bindingChatActivity.root)

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        val messageList: ArrayList<Message> = ArrayList()
        val messageAdapter = ChatAdapter(messageList)

        val chatRecyclerView = bindingChatActivity.chatRecyclerview
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter

        val name = intent.getStringExtra(ConstValue.EXTRA_NAME)
        val receiverUid = intent.getStringExtra(ConstValue.EXTRA_UID)

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        mDatabaseReference.child(ConstValue.PATH_CHATS_FIREBASE).child(senderRoom!!).child(ConstValue.PATH_MESSAGES_FIREBASE)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        bindingChatActivity.apply {
            sendButton.setOnClickListener {
                val message = editMessage.text.toString()
                val messageObject = Message(message, senderUid)

                mDatabaseReference.child(ConstValue.PATH_CHATS_FIREBASE)
                    .child(senderRoom!!)
                    .child(ConstValue.PATH_MESSAGES_FIREBASE)
                    .push()
                    .setValue(messageObject)
                    .addOnSuccessListener {
                        mDatabaseReference.child(ConstValue.PATH_CHATS_FIREBASE)
                            .child(receiverRoom!!)
                            .child(ConstValue.PATH_MESSAGES_FIREBASE)
                            .push()
                            .setValue(messageObject)
                    }
                editMessage.setText("")
            }


        }
    }
}