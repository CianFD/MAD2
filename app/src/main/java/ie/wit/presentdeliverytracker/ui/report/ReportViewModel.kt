package ie.wit.presentdeliverytracker.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val deliveriesList = MutableLiveData<List<DeliveryModel>>()

    val observableDeliveriesList: LiveData<List<DeliveryModel>>
        get() = deliveriesList

    init {
        load()
    }

    fun load() {
        try {
            DeliveryManager.findAll(deliveriesList)
            Timber.i("Retrofit Success : $deliveriesList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }

    fun delete(id: String) {
        try {
            DeliveryManager.delete(id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: java.lang.Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}