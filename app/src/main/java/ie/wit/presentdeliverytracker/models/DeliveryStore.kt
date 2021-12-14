package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData

interface DeliveryStore {
    fun findAll(deliveriesList: MutableLiveData<List<DeliveryModel>>)
    fun findById(id: Long) : DeliveryModel?
    fun create(delivery: DeliveryModel)
}