package id.co.mealdb.data.remote

import id.co.mealdb.data.remote.response.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealByCategory(
        @Query("c") category: String = "Seafood"
    ): MealResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealIngredients(
        @Query("i") idMeal: String
    ): MealResponse
}