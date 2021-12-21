package ie.wit.presentdeliverytracker.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.presentdeliverytracker.firebase.FirebaseDBManager
import ie.wit.presentdeliverytracker.models.DeliveryManager
import ie.wit.presentdeliverytracker.models.DeliveryModel
import timber.log.Timber

class ReportViewModel : ViewModel() {

    private val deliveriesList =
        MutableLiveData<List<DeliveryModel>>()

    val observableDeliveriesList: LiveData<List<DeliveryModel>>
        get() = deliveriesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init { load() }

    fun load() {
        try {
            DeliveryManager.findAll(liveFirebaseUser.value?.email!!, deliveriesList)
            Timber.i("Report Load Success : ${deliveriesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            //DonationManager.delete(userid,id)
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}