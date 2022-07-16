package id.co.mealdb.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.co.mealdb.data.remote.MealApi
import id.co.mealdb.data.repository.MealRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMealRepository(api: MealApi): MealRepository {
        return MealRepository(api)
    }
}