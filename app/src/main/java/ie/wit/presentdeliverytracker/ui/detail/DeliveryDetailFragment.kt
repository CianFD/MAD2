package ie.wit.presentdeliverytracker.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.wit.presentdeliverytracker.databinding.FragmentDeliveryDetailBinding
import timber.log.Timber


class DeliveryDetailFragment : Fragment() {

    private lateinit var detailViewModel: DeliveryDetailViewModel
    private val args by navArgs<DeliveryDetailFragmentArgs>()
    private var _fragBinding: FragmentDeliveryDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentDeliveryDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(DeliveryDetailViewModel::class.java)
        detailViewModel.observableDelivery.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render() {
        fragBinding.editMessage.setText("A Message")
        fragBinding.editPats.setText("0")
        fragBinding.deliveryvm = detailViewModel
        Timber.i("Retrofit fragBinding.deliveryvm == $fragBinding.deliveryvm")
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getDelivery(args.deliveryid)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}