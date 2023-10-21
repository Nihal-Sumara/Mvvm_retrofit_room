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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: DummyAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var dummyViewModel: DummyViewModel
    private var list = ArrayList<DataClass.Result>()
    private var authorList = ArrayList<Author>()
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

    fun insertData(view: View) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("Clicked", "insertData: Clicked")
            for (data in list) {
                authorList.add(Author(data.id, data.author))
            }
            database.authorDao().insert(authorList)
        }
    }

    fun showData(view: View) {
        Log.e("Clicked", "showData: Clicked")
        database.authorDao().select().observe(this@MainActivity) {
            if (!it.isNullOrEmpty()) {
                binding.tvData.text = it.toString()
            } else Log.e("Data", "Data is empty")
        }
    }
}
