package ie.wit.presentdeliverytracker.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class DeliveryModel(
    var uid: String? = "",
    var type: String = "N/A",
    var amount: Int = 0,
    var message: String = "a message",
    var pats: Int = 0,
    var email: String? = "joe@bloggs.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "type" to type,
            "amount" to amount,
            "message" to message,
            "pats" to pats,
            "email" to email
        )
    }
}

