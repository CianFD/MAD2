package ie.wit.presentdeliverytracker.main

import android.app.Application
import timber.log.Timber

class PresentDeliveryTrackerApp : Application() {



    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Timber.i("Present Delivery Tracker Application Started")
    }
}