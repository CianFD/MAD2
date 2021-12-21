package ie.wit.presentdeliverytracker.ui.delivery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.presentdeliverytracker.firebase.FirebaseDBManager
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel

class DeliveryViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addDelivery(firebaseUser: MutableLiveData<FirebaseUser>,
                    delivery: DeliveryModel) {
        status.value = try {
            FirebaseDBManager.create(firebaseUser,delivery)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}