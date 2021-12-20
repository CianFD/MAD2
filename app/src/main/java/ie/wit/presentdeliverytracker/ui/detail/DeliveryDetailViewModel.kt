package ie.wit.presentdeliverytracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel
import timber.log.Timber

class DeliveryDetailViewModel : ViewModel() {
    private val delivery = MutableLiveData<DeliveryModel>()

    var observableDelivery: LiveData<DeliveryModel>
        get() = delivery
        set(value) {delivery.value = value.value}

    fun getDelivery(email:String, id: String) {
        try {
            DeliveryManager.findById(email, id, delivery)
            Timber.i("Detail getDelivery() Success : ${delivery.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDelivery() Error : $e.message")
        }
    }

    fun updateDelivery(email:String, id: String,delivery: DeliveryModel) {
        try {
            DeliveryManager.update(email, id, delivery)
            Timber.i("Detail update() Success : $delivery")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}