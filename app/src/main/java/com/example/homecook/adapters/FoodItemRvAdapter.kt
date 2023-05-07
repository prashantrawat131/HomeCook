package com.example.homecook.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homecook.databinding.FoodItemBinding
import com.example.homecook.models.FoodItemModel
import com.example.homecook.repository.DataRepository

class FoodItemRvAdapter(private val items: ArrayList<FoodItemModel>) :
    RecyclerView.Adapter<FoodItemRvAdapter.FoodItemViewHolder>() {

    private lateinit var resourceContext: Context

    inner class FoodItemViewHolder(private val binding: FoodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            resourceContext = itemView.context
        }

        fun bind(position: Int) {
            val item = items[position]
            binding.foodName.text = item.name
            Glide.with(resourceContext)
                .load(AppCompatResources.getDrawable(resourceContext, item.image))
                .into(binding.foodImage)
            binding.addBtn.isVisible = item.count == 0
            binding.incrementLayout.isVisible = item.count > 0
            binding.count.text = item.count.toString()
            binding.foodPrice.text = "₹ ".plus(item.price.toString())
            binding.addBtn.setOnClickListener {
                item.count++
                updateItem(item)
                notifyItemChanged(position)
            }
            binding.plusBtn.setOnClickListener {
                item.count++
                updateItem(item)
                notifyItemChanged(position)
            }
            binding.minusBtn.setOnClickListener {
                item.count--
                updateItem(item)
                notifyItemChanged(position)
            }
        }
    }

    fun updateItem(item: FoodItemModel) {
        var index = -1
        DataRepository.foodItems.forEach {
            if (it.image == item.image) {
                index = DataRepository.foodItems.indexOf(it)
                return@forEach
            }
        }
        if (index != -1) {
            DataRepository.foodItems[index] = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FoodItemBinding.inflate(layoutInflater, parent, false)
        return FoodItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        if (holder is FoodItemViewHolder) {
            holder.bind(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}