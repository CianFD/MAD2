package ie.wit.presentdeliverytracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class DeliveryModel(var id: Long = 0,
                         @SerializedName("paymenttype")
                         val type: String = "N/A",
                         val amount: Int = 0) : Parcelable