package id.co.mealdb.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.co.mealdb.data.remote.response.MealResponse
import id.co.mealdb.data.repository.MealRepository
import id.co.mealdb.utils.Constants.ID_MEAL
import id.co.mealdb.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _meals = MutableLiveData<Resource<MealResponse>>()
    val meals: LiveData<Resource<MealResponse>> = _meals

    private val _meal = MutableLiveData<Resource<MealResponse>>()
    val meal: LiveData<Resource<MealResponse>> = _meal

    init {
        getMealByCategory()
        savedStateHandle.get<String>(ID_MEAL)?.let { idMeal ->
            getMealIngredients(idMeal)
        }
    }

    private fun getMealByCategory() = viewModelScope.launch {
        repository.getMealByCategory().asFlow().collect {
            _meals.postValue(it)
        }
    }

    private fun getMealIngredients(idMeal: String) = viewModelScope.launch {
        repository.getMealIngredients(idMeal).asFlow().collect {
            _meal.postValue(it)
        }
    }
}