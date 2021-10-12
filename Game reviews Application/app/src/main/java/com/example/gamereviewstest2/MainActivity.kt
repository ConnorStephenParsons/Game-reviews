package com.example.gamereviewstest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

//import android.widget.ListView
import com.example.gamereviewstest2.MyAdapter as MyAdapter

class MainActivity : AppCompatActivity(), MyAdapter.Listener {

    private var myAdapter: MyAdapter? = null
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myRetroGamesArrayList: ArrayList<RetroGames>? = null
    private val baseURL = "https://game-api-review.herokuapp.com/games/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        loadData()
    }

    //Initialise the RecyclerView//

    private fun initRecyclerView() {

//Use a layout manager to position the items to look like a standard ListView//

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        jsonList.layoutManager = layoutManager

    }

//Implement loadData//

    private fun loadData() {

//Define the Retrofit request//

        val requestInterface = Retrofit.Builder()

//Set the API’s base URL//

                .baseUrl(baseURL)

//Specify the converter factory to use for serialization and deserialization//

                .addConverterFactory(GsonConverterFactory.create())

//Add a call adapter factory to support RxJava return types//

                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

//Build the Retrofit instance//

                .build().create(GetData::class.java)

//Add all RxJava disposables to a CompositeDisposable//

        myCompositeDisposable?.add(requestInterface.getData()

//Send the Observable’s notifications to the main UI thread//

                .observeOn(AndroidSchedulers.mainThread())

//Subscribe to the Observer away from the main UI thread//

                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse))

    }

    private fun handleResponse(gamesList: List<RetroGames>) {

        myRetroGamesArrayList = ArrayList(gamesList)
        myAdapter = MyAdapter(myRetroGamesArrayList!!, this)

//Set the adapter//
//
        jsonList.adapter = myAdapter

    }

    override fun onItemClick(retroGames: RetroGames) {

//If the user clicks on an item, then display a Toast//

        Toast.makeText(this, "You clicked: ${retroGames.Game}", Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()

//Clear all the disposables//

        myCompositeDisposable?.clear()

    }

}



