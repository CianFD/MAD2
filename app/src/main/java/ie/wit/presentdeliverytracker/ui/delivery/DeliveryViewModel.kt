package ie.wit.presentdeliverytracker.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel

class DeliveryViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addDelivery(delivery: DeliveryModel) {
        status.value = try {
            DeliveryManager.create(delivery)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}