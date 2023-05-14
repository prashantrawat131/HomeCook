package com.example.homecook.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.homecook.databinding.OrderItemBinding
import com.example.homecook.models.OrderItemModel
import com.example.homecook.utils.CO

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

            val items = StringBuilder()
            for (item in order.foodItemsList) {
                items.append(item.name)
                items.append(", ")
            }
            binding.orderItems.text = items.toString().substring(0, items.length - 2)

            try {
                val currentTime = System.currentTimeMillis()
                val orderTime = order.orderTime
                val diff = currentTime - orderTime
                val timeToDeliver = 1000 * 60 * 10 * order.foodItemsList.size
                if (diff < timeToDeliver) {
                    binding.timeLeft.text = ((timeToDeliver - diff)/(1000*60)).toString().plus(" mins left")
                } else {
                    binding.timeLeft.text = "Delivered"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            binding.orderId.setOnClickListener {
                CO.copyToClipboard(binding.root.context, order.orderTime.toString())
            }
        }
    }

}