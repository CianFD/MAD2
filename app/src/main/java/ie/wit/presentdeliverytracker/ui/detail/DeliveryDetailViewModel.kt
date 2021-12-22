package ie.wit.presentdeliverytracker.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.presentdeliverytracker.firebase.FirebaseDBManager
import ie.wit.presentdeliverytracker.models.DeliveryModel
import timber.log.Timber

class DeliveryDetailViewModel : ViewModel() {
    private val delivery = MutableLiveData<DeliveryModel>()

    var observableDelivery: LiveData<DeliveryModel>
        get() = delivery
        set(value) {delivery.value = value.value}

    fun getDelivery(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, delivery)
            Timber.i("Detail getDelivery() Success : ${
                delivery.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getDelivery() Error : $e.message")
        }
    }

    fun updateDelivery(userid:String, id: String,delivery: DeliveryModel) {
        try {
            FirebaseDBManager.update(userid, id, delivery)
            Timber.i("Detail update() Success : $delivery")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}