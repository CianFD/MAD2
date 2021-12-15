package ie.wit.presentdeliverytracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryModel(var _id: String = "N/A",
                         @SerializedName("type")
                         val type: String = "N/A",
                         val message: String = "n/a",
                         val amount: Int = 0) : Parcelable