package com.example.homecook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homecook.databinding.CartItemBinding
import com.example.homecook.models.CartItemModel
import com.example.homecook.models.FoodItemModel

/*  Write a recycler view adapter for order items   */
class CartAdapter(
    private val items: ArrayList<CartItemModel>,
    private val addToCart: (CartItemModel) -> Unit,
    private val removeFromCart: (CartItemModel) -> Unit
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], position)

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItemModel, position: Int) {
            val foodItem = items[position].foodItem
            binding.apply {
                if (item.count == 0) {
                    root.layoutParams.height = 0
                    root.layoutParams.width = 0
                }
                name.text = foodItem?.name
                price.text = "₹ ".plus(foodItem?.price.toString())
                quantity.text = item.count.toString().plus(" items")
                count.text = item.count.toString()
                total.text = foodItem?.price?.times(item.count!!).toString()
                Glide.with(root.context).load(foodItem?.image).into(image)
                plusBtn.setOnClickListener {
                    item.count = item.count?.plus(1)
                    addToCart(item)
                    notifyItemChanged(position)
                }
                minusBtn.setOnClickListener {
                    item.count = item.count?.minus(1)
                    if (item.count == 0) {
                        root.layoutParams.height = 0
                        root.layoutParams.width = 0
                    }
                    removeFromCart(item)
                    notifyItemChanged(position)
                }
            }
        }
    }
}