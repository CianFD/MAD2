package ie.wit.presentdeliverytracker.main

import android.app.Application
import timber.log.Timber

class PresentDeliveryTrackerApp : Application() {

    //lateinit var deliveriesStore: DeliveryStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
      //  deliveriesStore = DeliveryManager()
        Timber.i("Present Delivery Tracker Application Started")
    }
}