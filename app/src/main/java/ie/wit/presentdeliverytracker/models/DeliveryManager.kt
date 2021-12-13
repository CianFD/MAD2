package ie.wit.presentdeliverytracker.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object DeliveryManager : DeliveryStore {

    private val deliveries = ArrayList<DeliveryModel>()

    override fun findAll(): List<DeliveryModel> {
        return deliveries
    }

    override fun findById(id:Long) : DeliveryModel? {
        val foundDelivery: DeliveryModel? = deliveries.find { it.id == id }
        return foundDelivery
    }

    override fun create(delivery: DeliveryModel) {
        delivery.id = getId()
        deliveries.add(delivery)
        logAll()
    }

    fun logAll() {
        Timber.v("** Deliveries List **")
        deliveries.forEach { Timber.v("Amount of Presents Added : ${it}") }
    }
}