package com.example.practiceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceapp.databinding.DummyItemBinding
import com.example.practiceapp.model.DataClass

class DummyAdapter(private val data: ArrayList<DataClass.Result>, private var click: ItemClick) :
    RecyclerView.Adapter<DummyAdapter.TestVH>() {
//    private var onItemClick: ((item: Int) -> Unit)? = null

    class TestVH(val binding: DummyItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestVH {
        return TestVH(DummyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TestVH, position: Int) {
        with(holder) {
            with(data[position]) {
                /*For biding adapter to bind data in xml*/
                binding.mResult = this
//                binding.tvItem.text = this.author
                binding.tvItem.setOnClickListener {
                    click.clickItem(adapterPosition)
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    interface ItemClick {
        fun clickItem(position: Int)
    }
}