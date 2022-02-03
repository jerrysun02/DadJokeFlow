package com.langlab.dadjokeflow.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.langlab.dadjokeflow.R
import com.langlab.dadjokeflow.model.Joke

class JokeListAdapter(private var jokes: List<Joke>) :
    RecyclerView.Adapter<JokeListAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeListAdapter.MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.joke_item, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokeListAdapter.MViewHolder, position: Int) {
        holder.bind(jokes[position])
    }

    override fun getItemCount(): Int {
        return jokes.size
    }

    fun update(data: List<Joke>) {
        jokes = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val content: TextView = view.findViewById(R.id.content)
        fun bind(joke: Joke) {
            title.text = joke.title
            content.text = joke.content
        }
    }
}