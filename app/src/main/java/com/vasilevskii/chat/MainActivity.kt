package com.vasilevskii.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.vasilevskii.chat.adapter.UserAdapter
import com.vasilevskii.chat.databinding.ActivityMainBinding
import com.vasilevskii.chat.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        userList = ArrayList()


        initFireBase()
        initRecycler()
        valueListenerDatabase()
    }

    private fun valueListenerDatabase() {
        mDatabaseReference.child(ConstValue.PATH_USER_FIREBASE).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (mFirebaseAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initFireBase() {
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference
    }

    private fun initRecycler() {
        userAdapter = UserAdapter(this, userList)

        bindingMainActivity.apply {
            userRecycler
            userRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            userRecycler.adapter = userAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout) {
            mFirebaseAuth.signOut()
            finish()
            startActivity(Intent(this@MainActivity, LogIn::class.java))
            return true
        }

        return true
    }
}