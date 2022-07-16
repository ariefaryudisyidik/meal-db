package id.co.mealdb.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.co.mealdb.R
import id.co.mealdb.databinding.FragmentHomeBinding
import id.co.mealdb.ui.MealAdapter
import id.co.mealdb.ui.MealViewModel
import id.co.mealdb.utils.Extension.toast
import id.co.mealdb.utils.Resource

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MealViewModel by viewModels()
    private lateinit var mealAdapter: MealAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        getMealByCategory()
        navigateToDetail()
    }

    private fun setupRecyclerView() {
        mealAdapter = MealAdapter()
        binding.rvMeal.adapter = mealAdapter
    }

    private fun getMealByCategory() {
        viewModel.meals.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    result.data?.let { mealAdapter.submitList(it.meals) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showMessage(result.message)
                }
            }
        }
    }

    private fun showLoading(visible: Boolean) {
        binding.progressBar.isVisible = visible
    }

    private fun showMessage(message: String?) {
        message?.toast(requireContext())
    }

    private fun navigateToDetail() {
        mealAdapter.setOnItemClickListener {
            findNavController().navigate(HomeFragmentDirections.toDetailFragment(it.idMeal))
        }
    }
}