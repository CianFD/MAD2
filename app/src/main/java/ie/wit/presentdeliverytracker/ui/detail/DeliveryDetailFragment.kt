package ie.wit.presentdeliverytracker.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import ie.wit.presentdeliverytracker.R

class DeliveryDetailFragment : Fragment() {

    companion object {
        fun newInstance() = DeliveryDetailFragment()
    }

    private lateinit var viewModel: DeliveryDetailViewModel
    private val args by navArgs<DeliveryDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_delivery_detail, container, false)

        Toast.makeText(context,"Delivery ID Selected : ${args.deliveryid}",Toast.LENGTH_LONG).show()

        return view
    }


}