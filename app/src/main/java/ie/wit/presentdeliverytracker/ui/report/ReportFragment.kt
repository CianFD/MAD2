package ie.wit.presentdeliverytracker.ui.report

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.presentdeliverytracker.R
import ie.wit.presentdeliverytracker.adapters.DeliveryAdapter
import ie.wit.presentdeliverytracker.adapters.DeliveryClickListener
import ie.wit.presentdeliverytracker.databinding.FragmentReportBinding
import ie.wit.presentdeliverytracker.main.PresentDeliveryTrackerApp
import ie.wit.presentdeliverytracker.models.DeliveryModel
import ie.wit.presentdeliverytracker.utils.SwipeToDeleteCallback
import ie.wit.presentdeliverytracker.utils.createLoader
import ie.wit.presentdeliverytracker.utils.hideLoader
import ie.wit.presentdeliverytracker.utils.showLoader

class ReportFragment : Fragment(), DeliveryClickListener {

    lateinit var app: PresentDeliveryTrackerApp
    private var _fragBinding: FragmentReportBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var reportViewModel: ReportViewModel
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReportBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        loader = createLoader(requireActivity())

        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        reportViewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        showLoader(loader,"Downloading Donations")
        reportViewModel.observableDeliveriesList.observe(viewLifecycleOwner, Observer {
                deliveries ->
            deliveries?.let {
                render(deliveries as ArrayList<DeliveryModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        fragBinding.fab.setOnClickListener {
            val action = ReportFragmentDirections.actionReportFragmentToDeliveryFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader,"Deleting Delivery")
                val adapter = fragBinding.recyclerView.adapter as DeliveryAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                reportViewModel.delete(viewHolder.itemView.tag as String)
                hideLoader(loader)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun render(deliveriesList: ArrayList<DeliveryModel>) {
        fragBinding.recyclerView.adapter = DeliveryAdapter(deliveriesList,this)
        if (deliveriesList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.deliveriesNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.deliveriesNotFound.visibility = View.GONE
        }
    }

    override fun onDeliveryClick(delivery: DeliveryModel) {
        val action = ReportFragmentDirections.actionReportFragmentToDeliveryDetailFragment(delivery._id)
        findNavController().navigate(action)
    }

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Deliveries")
            reportViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        reportViewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}