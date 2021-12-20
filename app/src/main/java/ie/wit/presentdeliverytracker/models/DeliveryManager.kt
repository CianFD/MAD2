package ie.wit.presentdeliverytracker.models

import androidx.lifecycle.MutableLiveData
import ie.wit.presentdeliverytracker.api.DeliveryClient
import ie.wit.presentdeliverytracker.api.DeliveryWrapper
import timber.log.Timber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DeliveryManager : DeliveryStore {

    override fun findAll(deliveriesList: MutableLiveData<List<DeliveryModel>>) {

        val call = DeliveryClient.getApi().findall()

        call.enqueue(object : Callback<List<DeliveryModel>> {
            override fun onResponse(
                call: Call<List<DeliveryModel>>,
                response: Response<List<DeliveryModel>>
            ) {
                deliveriesList.value = response.body() as ArrayList<DeliveryModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DeliveryModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findAll(email: String, deliveriesList: MutableLiveData<List<DeliveryModel>>) {

        val call = DeliveryClient.getApi().findall(email)

        call.enqueue(object : Callback<List<DeliveryModel>> {
            override fun onResponse(
                call: Call<List<DeliveryModel>>,
                response: Response<List<DeliveryModel>>
            ) {
                deliveriesList.value = response.body() as ArrayList<DeliveryModel>
                Timber.i("Retrofit findAll() = ${response.body()}")
            }

            override fun onFailure(call: Call<List<DeliveryModel>>, t: Throwable) {
                Timber.i("Retrofit findAll() Error : $t.message")
            }
        })
    }

    override fun findById(email: String, id: String, delivery: MutableLiveData<DeliveryModel>) {

        val call = DeliveryClient.getApi().get(email, id)

        call.enqueue(object : Callback<DeliveryModel> {
            override fun onResponse(call: Call<DeliveryModel>, response: Response<DeliveryModel>) {
                delivery.value = response.body() as DeliveryModel
                Timber.i("Retrofit findById() = ${response.body()}")
            }

            override fun onFailure(call: Call<DeliveryModel>, t: Throwable) {
                Timber.i("Retrofit findById() Error : $t.message")
            }
        })
    }

    override fun create(delivery: DeliveryModel) {

        val call = DeliveryClient.getApi().post(delivery.email, delivery)
        Timber.i("Retrofit ${call.toString()}")

        call.enqueue(object : Callback<DeliveryWrapper> {
            override fun onResponse(
                call: Call<DeliveryWrapper>,
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
                Timber.i("Retrofit create Error : $t.message")
            }
        })
    }

    override fun delete(email: String, id: String) {

        val call = DeliveryClient.getApi().delete(email, id)

        call.enqueue(object : Callback<DeliveryWrapper> {
            override fun onResponse(
                call: Call<DeliveryWrapper>,
                response: Response<DeliveryWrapper>
            ) {
                val deliveryWrapper = response.body()
                if (deliveryWrapper != null) {
                    Timber.i("Retrofit Delete ${deliveryWrapper.message}")
                    Timber.i("Retrofit Delete ${deliveryWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DeliveryWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    override fun update(email: String, id: String, delivery: DeliveryModel) {

        val call = DeliveryClient.getApi().put(email, id, delivery)

        call.enqueue(object : Callback<DeliveryWrapper> {
            override fun onResponse(
                call: Call<DeliveryWrapper>,
                response: Response<DeliveryWrapper>
            ) {
                val deliveryWrapper = response.body()
                if (deliveryWrapper != null) {
                    Timber.i("Retrofit Update ${deliveryWrapper.message}")
                    Timber.i("Retrofit Update ${deliveryWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<DeliveryWrapper>, t: Throwable) {
                Timber.i("Retrofit Update Error : $t.message")
            }
        })
    }
}