package ie.wit.presentdeliverytracker.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel

class ReportViewModel : ViewModel() {

    private val deliveriesList = MutableLiveData<List<DeliveryModel>>()

    val observableDeliveriesList: LiveData<List<DeliveryModel>>
        get() = deliveriesList

    init {
        load()
    }

    fun load() {
        deliveriesList.value = DeliveryManager.findAll()
    }
}