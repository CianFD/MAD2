package ie.wit.presentdeliverytracker.api

import ie.wit.presentdeliverytracker.models.DeliveryModel
import retrofit2.Call
import retrofit2.http.*

interface DeliveryService {
    @GET("/deliveries")
    fun getall(): Call<List<DeliveryModel>>

    @GET("/deliveries/{id}")
    fun get(@Path("id") id: String): Call<DeliveryModel>

    @DELETE("/deliveries/{id}")
    fun delete(@Path("id") id: String): Call<DeliveryWrapper>

    @POST("/deliveries")
    fun post(@Body delivery: DeliveryModel): Call<DeliveryWrapper>

    @PUT("/deliveries/{id}")
    fun put(@Path("id") id: String,
            @Body delivery: DeliveryModel
    ): Call<DeliveryWrapper>
}