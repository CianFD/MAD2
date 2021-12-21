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

class DeliveryAdapter constructor(private var deliveries: ArrayList<DeliveryModel>,
                                  private val listener: DeliveryClickListener,
                                  private val readOnly: Boolean)
    : RecyclerView.Adapter<DeliveryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDeliveryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding,readOnly)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val delivery = deliveries[holder.adapterPosition]
        holder.bind(delivery,listener)
    }

    fun removeAt(position: Int) {
        deliveries.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = deliveries.size

    inner class MainHolder(val binding : CardDeliveryBinding, private val readOnly : Boolean) :
        RecyclerView.ViewHolder(binding.root) {

        val readOnlyRow = readOnly

        fun bind(delivery: DeliveryModel, listener: DeliveryClickListener) {
            binding.root.tag = delivery
            binding.delivery = delivery
            binding.imageIcon.setImageResource(R.drawable.present)
            binding.root.setOnClickListener { listener.onDeliveryClick(delivery) }
            binding.executePendingBindings()
        }
    }
}