package com.tutorialsbuzz.fcmdemo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_subscribe.*
import kotlinx.android.synthetic.main.row_item.view.*

class SubscribeNotificationTopicsActivity : AppCompatActivity() {

    private lateinit var adapter: SubscriptionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_subscribe)

        subscriptionList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        subscriptionList.addItemDecoration(SimpleDividerItemDecoration(this))

        adapter = SubscriptionAdapter(readFromAsset(), this)
        subscriptionList.adapter = adapter

        adapter.setOnItemClickListener(object : SubscriptionAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {

                // status false user not subscribed
                if (!adapter.modelList.get(pos).status) {
                    subscribeToTopics(adapter.modelList.get(pos).name, pos)
                } else {
                    UnSubscribeToTopics(adapter.modelList.get(pos).name, pos)
                }

            }
        })
    }

    fun updateList(position: Int, status: Boolean) {

        adapter.modelList.get(position).status = status
        adapter.notifyItemChanged(position)

        val preferenceManager =
            PreferenceManager.getInstance(this@SubscribeNotificationTopicsActivity)
        preferenceManager?.storeJSONOptimization(Gson().toJson(adapter.modelList))

    }

    // subscribe for notification topic
    fun subscribeToTopics(topic: String, position: Int) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task -> //String msg = getString(R.string.msg_subscribed);
                if (task.isSuccessful) {
                    updateList(position, true)
                    showToast("Subscribe To " + topic)
                }
            }
    }

    // Unsubscribe for notification topic
    fun UnSubscribeToTopics(topic: String, position: Int) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task -> //String msg = getString(R.string.msg_subscribed);
                if (task.isSuccessful) {
                    updateList(position, false)
                    showToast("UnSubscribed For " + topic)
                }
            }
    }

    fun showToast(msg: String) {
        Toast.makeText(
            this@SubscribeNotificationTopicsActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun readFromAsset(): List<Model> {

        val modeList: MutableList<Model>
        val preferenceManager = PreferenceManager.getInstance(this);

        if (preferenceManager?.getStoredJSONOptimization() != null) {
            modeList = preferenceManager.getStoredJSONOptimization() as MutableList<Model>
        } else {
            //read from asset and store to preference
            val bufferReader = application.assets.open("subscription.json").bufferedReader()
            val json_string = bufferReader.use {
                it.readText()
            }

            modeList =
                Gson().fromJson(json_string, Array<Model>::class.java)
                    .toList() as MutableList<Model>
        }

        return modeList
    }

}