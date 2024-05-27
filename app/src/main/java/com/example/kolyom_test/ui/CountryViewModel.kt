package com.example.kolyom_test.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.kolyom_test.adapter.CountryAdapter
import com.example.kolyom_test.model.DataItem
import com.example.kolyom_test.model.ResponseModel
import com.example.kolyom_test.repository.CountryRepository
import com.example.kolyom_test.util.RequestState
import kotlinx.coroutines.launch
import retrofit2.Response

class CountryViewModel(
    val countryRepository: CountryRepository
) : ViewModel() {


    val countriesList: MutableLiveData<RequestState<ResponseModel>> = MutableLiveData()

    init {
        getCountryList()
    }

    fun getCountryList() = viewModelScope.launch {
        countriesList.postValue(RequestState.Loading())
        var response = countryRepository.getCountries()
        countriesList.postValue(handleResponse(response))

    }

    private fun handleResponse(response: Response<ResponseModel>): RequestState<ResponseModel> {
        if (response.isSuccessful) {
            response.body().let { result ->
                return RequestState.Sucess(result)
            }
        } else {
            return RequestState.Error(response.message())
        }
    }

    fun filterList(query: String?, countries: List<DataItem>, countryAdapter: CountryAdapter) {
        if (query != null) {
            val filteredList = ArrayList<DataItem>()
            for (i in countries) {
                if (i.nameEn!!.contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Log.e("ActivityMain", "empty list")
            } else {
                countryAdapter.countries = filteredList
            }
        } else {
            countryAdapter.countries = countries
        }
    }
}