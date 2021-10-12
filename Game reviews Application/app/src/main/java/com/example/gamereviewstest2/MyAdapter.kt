package com.example.gamereviewstest2

import android.content.Intent
import android.database.DataSetObserver
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout.view.*
import android.widget.ArrayAdapter
import android.widget.ListView

//Pass the ArrayList and a listener, and add a variable to hold the data//

class MyAdapter(
    private val gamesList: ArrayList<RetroGames>, private val listener:

//Extend RecyclerView.Adapter//

    Listener
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(retroGames: RetroGames)

    }

//Define an array of colours for UI look on the application//

    private val colors: Array<String> = arrayOf(
        "#36454F",
        "#36454F",
        "#36454F",
        "#36454F",
        "#36454F",
        "#36454F",
        "#36454F",
        "#36454F"
    )

//Bind the ViewHolder//

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//Pass the position where each item should be displayed//

        holder.bind(gamesList[position], listener, colors, position)

    }

//Check how many items there are to display//

    override fun getItemCount(): Int = gamesList.count()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)

    }

//Create a ViewHolder class for the RecyclerView items to allow activities to be made//

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        //Clickable JSON button to go to a different screen state to view the contents.
        init {
            view.setOnClickListener {
                val intent = Intent(view.context, GameDetailActivity::class.java)
                view.context.startActivity(intent)
            }
        }

//Assign values from the data model, to their corresponding Views//

        fun bind(retroGames: RetroGames, listener: Listener, colors: Array<String>, position: Int) {

//Listen for user input events//

            //itemView.setOnClickListener{ listener.onItemClick(retroGames) }

            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            itemView.text_Game.text = retroGames.Game
            itemView.text_Publisher.text = retroGames.Publisher
            itemView.text_Year.text = retroGames.Year

            ImageHolder(itemView).updateWithUrl("https://game-api-review.herokuapp.com/images/Halo.png")
        }

    }


}

