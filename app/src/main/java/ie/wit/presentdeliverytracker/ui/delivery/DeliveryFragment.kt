package ie.wit.presentdeliverytracker.ui.delivery

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import ie.wit.presentdeliverytracker.R
import ie.wit.presentdeliverytracker.databinding.FragmentDeliveryBinding
import ie.wit.presentdeliverytracker.models.DeliveryModel
import ie.wit.presentdeliverytracker.ui.auth.LoggedInViewModel
import ie.wit.presentdeliverytracker.ui.report.ReportViewModel

class DeliveryFragment : Fragment() {


    var totalDelivered = 0
    private var _fragBinding: FragmentDeliveryBinding? = null

    private val fragBinding get() = _fragBinding!!

    private val reportViewModel: ReportViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private lateinit var deliveryViewModel: DeliveryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragBinding = FragmentDeliveryBinding.inflate(inflater, container, false)
        val root = fragBinding.root


        deliveryViewModel = ViewModelProvider(this).get(DeliveryViewModel::class.java)
        deliveryViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })

        fragBinding.progressBar.max = 10000
        fragBinding.NumberOfPresents.minValue = 1
        fragBinding.NumberOfPresents.maxValue = 1000

        fragBinding.NumberOfPresents.setOnValueChangedListener { _, _, newVal ->

            fragBinding.presentAmount.setText("$newVal")
        }
        setButtonListener(fragBinding)
        return root;
    }


    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    //findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.addError),Toast.LENGTH_LONG).show()
        }
    }

    fun setButtonListener(layout: FragmentDeliveryBinding) {
        layout.addButton.setOnClickListener {
            val amount = if (layout.presentAmount.text.isNotEmpty())
                layout.presentAmount.text.toString().toInt() else layout.NumberOfPresents.value
            if(totalDelivered >= layout.progressBar.max)
                Toast.makeText(context,"Delivery Amount Exceeded!", Toast.LENGTH_LONG).show()
            else {
                val type = if(layout.type.checkedRadioButtonId == R.id.Gift) "Gift" else "Coal"
                totalDelivered += amount
                layout.totalSoFar.text = String.format(getString(R.string.totalSoFar),totalDelivered)
                layout.progressBar.progress = totalDelivered
                deliveryViewModel.addDelivery(loggedInViewModel.liveFirebaseUser,
                    DeliveryModel(type = type, amount = amount, email = loggedInViewModel.liveFirebaseUser.value?.email!!))

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
                requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()
        totalDelivered = reportViewModel.observableDeliveriesList.value!!.sumOf { it.amount }
        fragBinding.progressBar.progress = totalDelivered
        fragBinding.totalSoFar.text = String.format(getString(R.string.totalSoFar),totalDelivered)
    }
}