package com.example.gamereviewstest2
import io.reactivex.Observable
import retrofit2.http.GET
import java.util.*

interface GetData {

//Describe the request type and the relative URL//

    @GET("https://game-api-review.herokuapp.com/games/")
    fun getData() : Observable<List<RetroGames>>

}