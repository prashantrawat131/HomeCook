package com.example.homecook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homecook.databinding.OrderItemBinding
import com.example.homecook.models.OrderItemModel

class OrdersAdapter(private val ordersList: ArrayList<OrderItemModel>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private val TAG = "OrdersAdapterTAG"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    inner class ViewHolder(val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val order = ordersList[position]
            binding.orderId.text = "#${order.orderTime}"
            binding.totalPrice.text = "Total: ${order.totalPrice}"
            binding.paymentMethod.text = order.paymentMethod

            Glide.with(binding.root.context)
                .load(order.foodItemsList[0].image)
                .into(binding.orderItemImageview)

            binding.plusCount.text = when (order.foodItemsList.size) {
                1 -> ""
                else -> "+${order.foodItemsList.size - 1}"
            }

            try {
                val currentTime = System.currentTimeMillis()
                val orderTime = order.orderTime
                val diff = currentTime - orderTime
                val timeToDeliver = 1000 * 60 * 10 * order.foodItemsList.size
                if (diff < timeToDeliver) {
                    binding.timeLeft.text = (timeToDeliver - diff).toString()
                } else {
                    binding.timeLeft.text = "Delivered"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}