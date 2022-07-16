package id.co.mealdb.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import id.co.mealdb.R
import id.co.mealdb.data.remote.response.Meal
import id.co.mealdb.databinding.FragmentDetailBinding
import id.co.mealdb.ui.MealViewModel
import id.co.mealdb.utils.Extension.toast
import id.co.mealdb.utils.Resource

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MealViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        getMeal()
    }

    private fun getMeal() {
        viewModel.meal.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    result.data?.meals?.map { setDetail(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showMessage(result.message)
                }
            }
        }
    }

    private fun setDetail(meal: Meal) {
        binding.apply {
            clContents.visibility = View.VISIBLE
            Glide.with(requireView())
                .load(meal.strMealThumb)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivMeal)
            tvName.text = meal.strMeal
            tvCategory.text = meal.strCategory
            tvArea.text = meal.strArea
            tvInstruction.text = meal.strInstructions
            tvIngredients.text = getString(
                R.string.data_ingredients,
                meal.strIngredient1,
                meal.strIngredient2,
                meal.strIngredient3,
                meal.strIngredient4,
                meal.strIngredient5,
                meal.strIngredient6,
                meal.strIngredient7,
                meal.strIngredient8,
                meal.strIngredient9,
                meal.strIngredient10,
                meal.strIngredient11,
                meal.strIngredient12,
                meal.strIngredient13,
                meal.strIngredient14,
                meal.strIngredient15,
                meal.strIngredient16,
                meal.strIngredient17,
                meal.strIngredient18,
                meal.strIngredient19,
                meal.strIngredient20
            ).replace("null", "").trim()
            tvMeasures.text = getString(
                R.string.data_measure,
                meal.strMeasure1,
                meal.strMeasure2,
                meal.strMeasure3,
                meal.strMeasure4,
                meal.strMeasure5,
                meal.strMeasure6,
                meal.strMeasure7,
                meal.strMeasure8,
                meal.strMeasure9,
                meal.strMeasure10,
                meal.strMeasure11,
                meal.strMeasure12,
                meal.strMeasure13,
                meal.strMeasure14,
                meal.strMeasure15,
                meal.strMeasure16,
                meal.strMeasure17,
                meal.strMeasure18,
                meal.strMeasure19,
                meal.strMeasure20,
            ).replace("null", "").trim()
        }
    }

    private fun showLoading(visible: Boolean) {
        binding.progressBar.isVisible = visible
    }

    private fun showMessage(message: String?) {
        message?.toast(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}