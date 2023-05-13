package com.example.homecook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homecook.databinding.CartItemBinding
import com.example.homecook.models.FoodItemModel

/*  Write a recycler view adapter for order items   */
class CartAdapter(
    private val orders: ArrayList<FoodItemModel>,
    private val updateItem: (FoodItemModel) -> Unit
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(orders[position], position)

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: FoodItemModel, position: Int) {
            binding.apply {
                if (order.count == 0) {
                    root.layoutParams.height = 0
                    root.layoutParams.width = 0
                }
                name.text = order.name
                price.text = "₹ ".plus(order.price.toString())
                quantity.text = order.count.toString().plus(" items")
                count.text = order.count.toString()
                total.text = order.price.times(order.count).toString()
                Glide.with(root.context).load(order.image).into(image)
                plusBtn.setOnClickListener {
                    order.count++
                    updateItem(order)
                    notifyItemChanged(position)
                }
                minusBtn.setOnClickListener {
                    order.count--
                    updateItem(order)
                    notifyItemChanged(position)
                }
            }
        }
    }
}