package com.tutorialsbuzz.fcmdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*

class SubscriptionAdapter(val modelList: List<Model>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewHolder = (holder as ViewHolder)
        itemViewHolder.bind(modelList.get(position));
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return modelList.size;
    }


    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        fun bind(model: Model): Unit {
            itemView.subscribeLabel.text = "${(adapterPosition + 1)} ) ${model.name}"

            if (model.status) {
                itemView.subscribe.setText(R.string.subscribed)
                itemView.subscribe.setTextColor(ContextCompat.getColor(context, R.color.subscribed))
            } else {
                itemView.subscribe.setText(R.string.subscribe)
                itemView.subscribe.setTextColor(ContextCompat.getColor(context, R.color.subscribe))
            }

            itemView.subscribe.visibility = View.VISIBLE
            itemView.progressBar.visibility = View.GONE
            itemView.subscribe.setOnClickListener(this)
        }

        override fun onClick(view: View?) {

            if (view?.id == itemView.subscribe.id) {

                itemView.subscribe.visibility = View.GONE
                itemView.progressBar.visibility = View.VISIBLE

                mClickListener.onClick(adapterPosition, itemView)
            }

        }
    }

}
