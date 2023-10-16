package com.example.practiceapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.practiceapp.adapter.DummyAdapter
import com.example.practiceapp.databinding.ActivityMainBinding
import com.example.practiceapp.local.Author
import com.example.practiceapp.local.AuthorDatabase
import com.example.practiceapp.model.DataClass
import com.example.practiceapp.repository.DummyRepository
import com.example.practiceapp.viewmodel.DummyVMFactory
import com.example.practiceapp.viewmodel.DummyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DummyAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var dummyViewModel: DummyViewModel
    private var list = ArrayList<DataClass.Result>()
    private lateinit var author: Author
    private lateinit var database: AuthorDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AuthorDatabase.getDatabaseInstance(this)
        dummyViewModel =
            ViewModelProvider(this, DummyVMFactory(DummyRepository()))[DummyViewModel::class.java]
        dummyViewModel.getDataFromApi()
        showHideProgressBar()
        observeApiData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun observeApiData() {
        dummyViewModel.showData.observe(this) {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                insertDataIntoDB(it)
                adapter = DummyAdapter(it, object : DummyAdapter.ItemClick {
                    override fun clickItem(position: Int) {
                        Log.e("Position", list[position].author)
                    }
                })
                binding.rvData.adapter = adapter
            } else {
                dummyViewModel.hideProgressbar()
                Log.e("Data", "Data is null")
            }
        }
    }

    private fun showHideProgressBar() {
        dummyViewModel.showProgress.observe(this) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else binding.progressBar.visibility = View.GONE
        }
    }

    @DelicateCoroutinesApi
    private fun insertDataIntoDB(data: ArrayList<DataClass.Result>) {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                author = Author(0, "nihal")
                database.authorDao().insert(author)
//                for (i in data) {
//                    author = Author(0, "nihal")
//                    database.authorDao().insert(author)
                }
            }
        }
    }
