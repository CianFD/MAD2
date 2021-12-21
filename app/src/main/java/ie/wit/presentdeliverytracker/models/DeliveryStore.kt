package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface DeliveryStore {
    fun findAll(deliveriesList:
                MutableLiveData<List<DeliveryModel>>)
    fun findAll(userid:String,
                deliveriesList:
                MutableLiveData<List<DeliveryModel>>)
    fun findById(userid:String, deliveryid: String,
                 delivery: MutableLiveData<DeliveryModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, delivery: DeliveryModel)
    fun delete(userid:String, deliveryid: String)
    fun update(userid:String, deliveryid: String, delivery: DeliveryModel)
}