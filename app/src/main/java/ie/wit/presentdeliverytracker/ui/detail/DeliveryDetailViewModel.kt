package ie.wit.presentdeliverytracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel

class DeliveryDetailViewModel : ViewModel() {
    private val delivery = MutableLiveData<DeliveryModel>()

    val observableDelivery: LiveData<DeliveryModel>
        get() = delivery

    fun getDelivery(id: String) {
        delivery.value = DeliveryManager.findById(id)
    }
}