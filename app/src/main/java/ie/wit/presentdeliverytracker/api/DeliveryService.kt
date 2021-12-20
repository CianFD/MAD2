package ie.wit.presentdeliverytracker.api

import ie.wit.presentdeliverytracker.models.DeliveryModel
import retrofit2.Call
import retrofit2.http.*

interface DeliveryService {
    @GET("/deliveries")
    fun findall(): Call<List<DeliveryModel>>

    @GET("/deliveries/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<DeliveryModel>>

    @GET("/deliveries/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<DeliveryModel>

    @DELETE("/deliveries/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<DeliveryWrapper>

    @POST("/deliveries/{email}")
    fun post(@Path("email") email: String?,
             @Body delivery: DeliveryModel)
            : Call<DeliveryWrapper>

    @PUT("/deliveries/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body delivery: DeliveryModel
    ): Call<DeliveryWrapper>
}