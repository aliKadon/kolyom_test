package com.example.kolyom_test.repository

import androidx.lifecycle.ViewModel
import com.example.kolyom_test.data.RetrofitInstancs

class CountryRepository : ViewModel() {

    suspend fun getCountries() = RetrofitInstancs.api.getCountries()
}