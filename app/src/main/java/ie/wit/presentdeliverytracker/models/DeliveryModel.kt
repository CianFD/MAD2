package ie.wit.presentdeliverytracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DeliveryModel(var id: Long = 0,
                         val type: String = "N/A",
                         val amount: Int = 0) : Parcelable