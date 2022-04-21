package com.sg.pager20.utilities

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.okhttp.internal.DiskLruCache
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class Utilities1 {

    lateinit var doc: QuerySnapshot
  //  private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var num=0
    var c=562
    lateinit var deffered: Deferred<Int>

    suspend fun chkIfUserNameExist2(nameString: String): Int {

     num=5

        coroutineScope {


         val docRef = FirebaseFirestore.getInstance().collection(USER_REF)
                    .whereEqualTo(USER_USERNAME, nameString).limit(1).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            c=204
                        }
                    }

                            deffered = async(Dispatchers.IO) {
                             //   var bo = task.result.isEmpty


                                return@async c
                            }
                        }

   //     }
        return deffered.await()
    }
}
/* class UserDataMenager2 {
   var a=0
    lateinit var deffered :Deferred<Int>
    suspend fun getTotalUserCount():Int{
        // this is c  not C     in the coroutine
        coroutineScope {
             deffered=async (Dispatchers.IO){
                delay(1000)
                a=102
              var c=a
                return@async c
            }
        }
        return deffered.await()
    }
}*/

/* async function docExists(docName, docId) {
      const docRef = db.collection(docName).doc(docId);
      let docSnapshot = await docRef.get();
      if (docSnapshot.exists) {
          return true;
      } else {
          return false;
      }
  }*/


/* private fun chkIfUserNameExist1(): Boolean {
        // util.logi("RegisterActivity 92 ")
        /*firestore.collection("users").whereEqualTo("id", YOUR_USER_ID).limit(1).get()
.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                      if (task.isSuccessful()) {
                        // You can check here if document exist.
                        // It will be empty if it doesnt.
                        boolean isEmpty = task.getResult().isEmpty();
                      }
                    }
                });
           */
        var userN = binding.usernameRegister.text.toString()
      //  val gl= FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,userN).limit(1).get()
        val existUserDeffer=doWorkAsync(userN)

        val exsitUser=existUserDeffer.await()



      /*  FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,userN).limit(1).get()
            .addOnCompleteListener { task->
                if (task.isSuccessful()){
                    val isEmpty=task.result.isEmpty
                }
            }*/

         return false

    }

    fun doWorkAsync(userN: String): Deferred<Int> = GlobalScope.async {
          val sami= FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,userN).limit(1).get()
      return@async 42
}


   /* async function docExists(docName, docId) {
        const docRef = db.collection(docName).doc(docId);
        let docSnapshot = await docRef.get();
        if (docSnapshot.exists) {
            return true;
        } else {
            return false;
        }
    }*/



       suspend fun findIfExsust(name:String):Boolean{
        val sami= FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,name).limit(1).get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful){

                }



            }
    }
 */
