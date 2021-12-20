package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData

interface DeliveryStore {
    fun findAll(deliveriesList: MutableLiveData<List<DeliveryModel>>)
    fun findById(email:String, id: String, delivery: MutableLiveData<DeliveryModel>)
    fun findById(id: String) : DeliveryModel?
    fun create(delivery: DeliveryModel)
    fun delete(email: String,id: String)
    fun update(email:String,id: String,delivery: DeliveryModel)
}