package com.example.kolyom_test.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kolyom_test.R
import com.example.kolyom_test.adapter.CountryAdapter
import com.example.kolyom_test.databinding.ActivityMainBinding
import com.example.kolyom_test.model.DataItem
import com.example.kolyom_test.repository.CountryRepository
import com.example.kolyom_test.util.RequestState

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: CountryViewModel

    private lateinit var countryAdapter: CountryAdapter
    var countryList = mutableListOf<DataItem>()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        val repository = CountryRepository()
        val countryViewModelProviderFactory = CountryViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, countryViewModelProviderFactory).get(CountryViewModel::class.java)


        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        setupRecycleView()

        viewModel.countriesList.observe(this, Observer { response ->
            when(response) {
                is RequestState.Sucess -> {
                    binding.pvProgressBar.isVisible = false
                    response.data?.let {countriesList ->
                        countryAdapter.countries = countriesList.data!!
                        countryList = (countriesList.data as MutableList<DataItem>?)!!

                    }
                }
                is RequestState.Error -> {
                    binding.pvProgressBar.isVisible = false
                    response.message?.let {
                        Toast.makeText(this, "error $it", Toast.LENGTH_SHORT).show()
                    }

                }

                is RequestState.Loading -> {
                    binding.pvProgressBar.isVisible = true
                    Log.e("MainActivity","Loading...")
                }
            }

        })

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterList(newText,countryList,countryAdapter)
                return true
            }

        })
    }

    private fun setupRecycleView() = binding.rvCountry.apply {
        countryAdapter = CountryAdapter()
        adapter = countryAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}