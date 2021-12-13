package ie.wit.presentdeliverytracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.presentdeliverytracker.R
import ie.wit.presentdeliverytracker.databinding.CardDeliveryBinding
import ie.wit.presentdeliverytracker.models.DeliveryModel

interface DeliveryClickListener {
    fun onDeliveryClick(delivery: DeliveryModel)
}

class DeliveryAdapter constructor(private var deliveries: List<DeliveryModel>,
                                  private val listener: DeliveryClickListener)
    : RecyclerView.Adapter<DeliveryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDeliveryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val delivery = deliveries[holder.adapterPosition]
        holder.bind(delivery,listener)
    }

    override fun getItemCount(): Int = deliveries.size

    inner class MainHolder(val binding : CardDeliveryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: DeliveryModel, listener: DeliveryClickListener) {
            binding.delivery = delivery
            binding.imageIcon.setImageResource(R.mipmap.ic_launcher_round)
            binding.root.setOnClickListener { listener.onDeliveryClick(delivery) }
            binding.executePendingBindings()
        }
    }
}