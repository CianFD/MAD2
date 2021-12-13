package ie.wit.presentdeliverytracker.models

interface DeliveryStore {
    fun findAll() : List<DeliveryModel>
    fun findById(id: Long) : DeliveryModel?
    fun create(delivery: DeliveryModel)
}