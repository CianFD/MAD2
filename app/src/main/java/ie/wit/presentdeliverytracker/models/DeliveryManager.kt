package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData
import ie.wit.presentdeliverytracker.api.DeliveryClient
import ie.wit.presentdeliverytracker.api.DeliveryWrapper
import timber.log.Timber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object DeliveryManager : DeliveryStore {

    private val deliveries = ArrayList<DeliveryModel>()

    override fun findAll(deliveriesList: MutableLiveData<List<DeliveryModel>>) {

        val call = DeliveryClient.getApi().getall()

        call.enqueue(object : Callback<List<DeliveryModel>> {
            override fun onResponse(call: Call<List<DeliveryModel>>,
                                    response: Response<List<DeliveryModel>>
            ) {
                deliveriesList.value = response.body() as ArrayList<DeliveryModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DeliveryModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:Long) : DeliveryModel? {
        val foundDelivery: DeliveryModel? = deliveries.find { it.id == id }
        return foundDelivery
    }

    override fun create(delivery: DeliveryModel) {

        val call = DeliveryClient.getApi().post(delivery)

        call.enqueue(object : Callback<DeliveryWrapper> {
            override fun onResponse(call: Call<DeliveryWrapper>,
                                    response: Response<DeliveryWrapper>
            ) {
                val deliveryWrapper = response.body()
                if (deliveryWrapper != null) {
                    Timber.i("Retrofit ${deliveryWrapper.message}")
                    Timber.i("Retrofit ${deliveryWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DeliveryWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Deliveries List **")
        deliveries.forEach { Timber.v("Amount of Presents Added : ${it}") }
    }
}