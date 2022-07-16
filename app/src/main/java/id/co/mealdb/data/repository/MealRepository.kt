package id.co.mealdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.co.mealdb.data.remote.MealApi
import id.co.mealdb.data.remote.response.MealResponse
import id.co.mealdb.utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val api: MealApi
) {
    fun getMealByCategory(): LiveData<Resource<MealResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getMealByCategory()
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage!!))
        } catch (e: IOException) {
            emit(Resource.Error("No Internet Connection"))
        }
    }

    fun getMealIngredients(idMeal: String): LiveData<Resource<MealResponse>> = liveData {
        try {
            emit(Resource.Loading())
            val data = api.getMealIngredients(idMeal)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage!!))
        } catch (e: IOException) {
            emit(Resource.Error("No Internet Connection"))
        }
    }

}