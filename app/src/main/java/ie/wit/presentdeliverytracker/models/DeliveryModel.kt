package ie.wit.presentdeliverytracker.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryModel(
    val _id: String = "N/A",
    @SerializedName("type")
    val type: String = "N/A",
    var message: String = "n/a",
    var amount: Int = 0,
    var pats: Int = 0,
    val email: String = "joe@bloggs.com") : Parcelable

