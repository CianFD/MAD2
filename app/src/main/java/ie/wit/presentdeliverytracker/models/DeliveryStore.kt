package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData

interface DeliveryStore {
    fun findAll(deliveriesList: MutableLiveData<List<DeliveryModel>>)
    fun findById(id: String) : DeliveryModel?
    fun create(delivery: DeliveryModel)
    fun delete(id: String)
}