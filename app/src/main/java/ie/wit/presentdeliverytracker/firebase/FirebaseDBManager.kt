package ie.wit.presentdeliverytracker.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.presentdeliverytracker.models.DeliveryModel
import ie.wit.presentdeliverytracker.models.DeliveryStore
import timber.log.Timber

object FirebaseDBManager : DeliveryStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference


    override fun findAll(deliverieList: MutableLiveData<List<DeliveryModel>>) {
        database.child("deliveries")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Delivery error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<DeliveryModel>()
                    val children = snapshot.children
                    children.forEach {
                        val delivery = it.getValue(DeliveryModel::class.java)
                        localList.add(delivery!!)
                    }
                    database.child("deliveries")
                        .removeEventListener(this)

                    deliverieList.value = localList
                }
            })
    }

    override fun findAll(userid: String, deliveriesList: MutableLiveData<List<DeliveryModel>>) {

        database.child("user-deliveries").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Delivery error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<DeliveryModel>()
                    val children = snapshot.children
                    children.forEach {
                        val delivery = it.getValue(DeliveryModel::class.java)
                        localList.add(delivery!!)
                    }
                    database.child("user-deliveries").child(userid)
                        .removeEventListener(this)

                    deliveriesList.value = localList
                }
            })
    }

    override fun findById(userid: String, deliveryid: String, delivery: MutableLiveData<DeliveryModel>) {

        database.child("user-deliveries").child(userid)
            .child(deliveryid).get().addOnSuccessListener {
                delivery.value = it.getValue(DeliveryModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, delivery: DeliveryModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("deliveries").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        delivery.uid = key
        val deliveryValues = delivery.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/deliveries/$key"] = deliveryValues
        childAdd["/user-deliveries/$uid/$key"] = deliveryValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, deliveryid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/deliveries/$deliveryid"] = null
        childDelete["/user-deliveries/$userid/$deliveryid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, deliveryid: String, delivery: DeliveryModel) {

        val deliveryValues = delivery.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["deliveries/$deliveryid"] = deliveryValues
        childUpdate["user-deliveries/$userid/$deliveryid"] = deliveryValues

        database.updateChildren(childUpdate)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userDeliveries = database.child("user-deliveries").child(userid)
        val allDeliveries = database.child("deliveries")

        userDeliveries.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {

                        it.ref.child("profilepic").setValue(imageUri)

                        val delivery = it.getValue(DeliveryModel::class.java)
                        allDeliveries.child(delivery!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
}