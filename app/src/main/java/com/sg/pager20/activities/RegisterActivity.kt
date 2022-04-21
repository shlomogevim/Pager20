package com.sg.pager20.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import coil.request.Disposable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sg.pager20.R
import com.sg.pager20.databinding.ActivityRegisterBinding
import com.sg.pager20.models.Post
import com.sg.pager20.utilities.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    val util = Utility()
    val uil10=Utilities1()
 // val utilC = UtilCoutine()
    val posts = ArrayList<Post>()
    private val coroutinScope = CoroutineScope(Dispatchers.Main)
    var fullNameString = ""
    var nameString = ""
    var emailString = ""
    var passwordString = ""
    var emptyTV: Boolean = false
    var userExist  : Boolean = false
    var bo3: Boolean = false
    var bo4: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        demiData()

        binding.btnRegister.setOnClickListener {
        }
        binding.saveRegisterBtn.setOnClickListener {
            coroutinScope.launch {
                newUserRegistration()

            }
        }
      testBtn()
    }

    private fun demiData() {
        binding.usernameRegister.setText("")
        //  binding.usernameRegister.setText("Papi100")
        binding.mailRegister.setText("pap100@papi1.gmail")
        binding.passwordRegister.setText("111111")
    }

    private fun readData() {
        nameString = binding.usernameRegister.getText().toString()
        emailString = binding.mailRegister.getText().toString()
        passwordString = binding.passwordRegister.getText().toString()
        // util.logi("RegisterActivity  98  inside  readdata  ==> emailString=${emailString}")
    }



    suspend fun newUserRegistration() {
        readData()
        bo3=false
       emptyTV = chkIfTextViewEmpty()
        println("gg:  RegisterActivity newUserRegisteration  79            emptyTV=$emptyTV      nameString=$nameString     emailString=$emailString    passwordString=$passwordString")
        if (!emptyTV){
          CoroutineScope(Dispatchers.Main).launch {
               // userExist= uil10.chkIfUserNameExist2(nameString)
                var num= uil10.chkIfUserNameExist2(nameString)
                println("gg:  RegisterActivity newUserRegisteration  85               num=$num   nameString=$nameString  ")
               /* if (userExist){
                    util.createDialog(this@RegisterActivity, 12)
                }*/
           }

        }









      //  if (!bo1 && !bo2){

             //util.logi("RegisterActivity  80  inside  newUserRegistration   ==> bo2=$bo2")



       // }

    }

    private fun chkIfTextViewEmpty(): Boolean {
        var bol = false
//       util.logi("RegisterActivity 103  inside  chkIfTextViewEmpty      =======>   bol=${nameString==""} ")
        when {
            nameString.isEmpty() -> {
               util.createDialog(this, 6)
                bol = true
            }
            emailString == "" -> {
               util.createDialog(this, 7)
                bol = true
            }
            passwordString == "" -> {
              util.createDialog(this, 8)
                bol = true
            }
        }
       // util.logi("RegisterActivity 100 inside  chkIfTextViewEmpty      =======>   bol=$bol ")
        return bol
    }

   /* private fun chkIfUserNameExist(): Boolean {
        // util.logi("RegisterActivity 92 ")
        var userN = nameString
        var boo = false
        FirebaseFirestore.getInstance().collection(USER_REF).addSnapshotListener { value, error ->
            if (value != null) {
                boo=false
                for (doc in value.documents) {
                    var user = util.retrieveUserFromFirestore(doc)
                    if (user.userName == userN) {
                        boo = true
                        util.logi("RegisterActivity 131  inside  chkIfUserNameExist   ==> boo=$boo")

                        util.createDialog(this, 12)

                    }
                }
            }
        }
       util.logi("RegisterActivity 139      inside   chkIfUserNameExist ==============> boo=$boo ")
        return boo
    }*/

    private fun chkIfUserNameExist1(): Boolean {
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
//        val existUserDeffer=doWorkAsync(userN)

//        val exsitUser=existUserDeffer.await()



      /*  FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,userN).limit(1).get()
            .addOnCompleteListener { task->
                if (task.isSuccessful()){
                    val isEmpty=task.result.isEmpty
                }
            }*/

         return false

    }

   /* fun doWorkAsync(userN: String): Deferred<Int> = GlobalScope.async {
          val sami= FirebaseFirestore.getInstance().collection().whereEqualTo(USER_USERNAME,userN).limit(1).get()
      return@async 42
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



    fun creaUserInFirestore(bo:Boolean):Boolean{
        var boo=bo

        return boo
    }


    private fun createNewUser(bo:Boolean): Boolean {
       var  boo=bo
        boo=true
        // util.logi("RegisterActivity 110    email=$email            password=$password ")
        auth.createUserWithEmailAndPassword(emailString, passwordString)
            .addOnSuccessListener { result ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(nameString)
                    .build()
                result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                    //   util.logi("RegisterActivity  94  failler ==> ${it.localizedMessage}")
                }
                saveUserInfo(fullNameString, nameString, emailString, passwordString)
            }.addOnFailureListener {
                boo = false
                //  util.logi("RegisterActivity  133   inside createNewUser  ==> ${it.localizedMessage}")
                val st1 = "The email address is already in use by another account."
                val st2 =
                    "The given password is invalid. [ Password should be at least 6 characters ]"
                val st3 = "The email address is badly formatted."
              //  util.logi("RegisterActivity  167  inside  creatNewUser  ==> ${it.localizedMessage}")
              //  util.logi("RegisterActivity  168  inside  creatNewUser  ==> emailString=${emailString}")
                if (it.localizedMessage == st1) {
                    util.createDialog(this, 9)
                }
                if (it.localizedMessage == st2) {
                    util.createDialog(this, 10)
                }
                if (it.localizedMessage == st3) {
                    util.createDialog(this, 11)
                }
            }
        //util.logi("RegisterActivity  154   inside createNewUser      bol=$boo")
        return boo
    }

    /*  fun newUserRegistration() {
        readData()
        /*  coroutinScope.launch {
            val originalDeffer = coroutinScope.async(Dispatchers.IO) { getOriginalBitmap() }
            val originalBitmap = originalDeffer.await()

            val afterFilterDiffer=coroutinScope.async(Dispatchers.Default) {Filter.apply(originalBitmap)}
            val afterFilter=afterFilterDiffer.await()

            loadImg(afterFilter)
        }*/
        var bol1=false
        var bol2=false
        var bol3=false
        coroutinScope.launch {
                val bo1Deffer=coroutinScope.async(Dispatchers.Default) { chkIfTextViewEmpty() }
            /*    bol1= bo1Deffer.await()
            val bo2Deffer=coroutinScope.async (Dispatchers.Default){chkIfUserNameExist()  }
              bol2=bo2Deffer.await()
        val bol13Deffer=coroutinScope.async (Dispatchers.Default){createNewUser()  }
             bol3=bol13Deffer.await()*/

        }

      /*  if (!bol1 && !bol2 && bol3){
            util.createDialog(this, 13)
        }*/


    }

    private fun readData() {
        nameString = binding.usernameRegister.getText().toString()
        emailString = binding.mailRegister.getText().toString()
        passwordString = binding.passwordRegister.getText().toString()
       // util.logi("RegisterActivity  98  inside  readdata  ==> emailString=${emailString}")
    }

    private suspend fun chkIfTextViewEmpty(): Boolean {
        var bol =false
//       util.logi("RegisterActivity 103  inside  chkIfTextViewEmpty      =======>   bol=${nameString==""} ")
        when {
                 nameString.isEmpty() -> {

//                util.logi("RegisterActivity 109  inside  chkIfTextViewEmpty      =======>   bol=${nameString==""} ")
                withContext(Dispatchers.Main){
                       binding.usernameRegister.setText("נא להכניס שם משתמש ....")
                }

//                util.logi("RegisterActivity 113  inside  chkIfTextViewEmpty      =======>   bol=${nameString==""} ")

                bol =true
            }

            emailString == "" -> {
                utilC.createDialog(this, 7)
                bol = true
            }
            passwordString == "" -> {
                utilC.createDialog(this, 8)
                bol =true
            }
        }

        util.logi("RegisterActivity 100 inside  chkIfTextViewEmpty      =======>   bol=$bol ")
        return bol
    }

    private fun chkIfUserNameExist(): Boolean {
        // util.logi("RegisterActivity 92 ")
        var userN = binding.usernameRegister.text.toString()
        var bol = false
        FirebaseFirestore.getInstance().collection(USER_REF).addSnapshotListener { value, error ->
            if (value != null) {
                for (doc in value.documents) {
                    var user = util.retrieveUserFromFirestore(doc)
                    if (user.userName == userN) {
                        bol = true
                    }
                }
            }
        }
        util.logi("RegisterActivity 119      inside   chkIfUserNameExist ==============> bol=$bol ")
        return bol
    }


    private fun createNewUser(): Boolean {
        var bol = true
        // util.logi("RegisterActivity 110    email=$email            password=$password ")
        auth.createUserWithEmailAndPassword(emailString, passwordString)
            .addOnSuccessListener { result ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(nameString)
                    .build()
                result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                    //   util.logi("RegisterActivity  94  failler ==> ${it.localizedMessage}")
                }
                saveUserInfo(fullNameString, nameString, emailString, passwordString)
            }.addOnFailureListener {
                bol = false
              //  util.logi("RegisterActivity  133   inside createNewUser  ==> ${it.localizedMessage}")
                val st1 = "The email address is already in use by another account."
                val st2 =  "The given password is invalid. [ Password should be at least 6 characters ]"
                val st3 = "The email address is badly formatted."
                util.logi("RegisterActivity  167  inside  creatNewUser  ==> ${it.localizedMessage}")
                util.logi("RegisterActivity  168  inside  creatNewUser  ==> emailString=${emailString}")
                if (it.localizedMessage == st1) {
                    util.createDialog(this, 9)
                }
                if (it.localizedMessage == st2) {
                    util.createDialog(this, 10)
                }
                if (it.localizedMessage == st3) {
                    util.createDialog(this, 11)
                }
            }
        util.logi("RegisterActivity  154   inside createNewUser      bol=$bol")
        return bol
    }
*/


    private fun chkUserNamePlus(bo: Boolean): Boolean {
        var userN = binding.usernameRegister.text.toString()
        var boIn = bo
        FirebaseFirestore.getInstance().collection(USER_REF).addSnapshotListener { value, error ->
            if (value != null) {
                for (doc in value.documents) {
                    var user = util.retrieveUserFromFirestore(doc)
                    if (user.userName == userN) {
                        boIn = true
                    }
                }
            }
        }
        return boIn
    }


    private fun chkUserName(arr: ArrayList<Int>): ArrayList<Int> {
        arr[1] = 7

        return arr
    }

    /*private fun createAccount() {
        var arr = arrayListOf<Int>(1, 2)
        val arr1: ArrayList<Int> = chkUserName(arr)
        if (arr1[1] == 1) {
            util.createDialog(this, 12)
        } else {
            createRealAcounte()
        }
    }*/


    private fun boUser(boU: Boolean): Boolean {
        val bol = boU





        return bol
    }


    /*private fun createRealAcounte() {
        userNameString = binding.usernameRegister.text.toString()

        var bo = false
        when {

            userNameString.isEmpty() -> {
                util.createDialog(this, 6)
            }
            //TextUtils.isEmpty(email) ->{
            email.isEmpty() -> {
                util.createDialog(this, 7)
            }
            password.isEmpty() -> {
                util.createDialog(this, 8)
            }

            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->
                        val changeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(userNameString)
                            .build()
                        result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                            util.logi("RegisterActivity  94  failler ==> ${it.localizedMessage}")
                        }
                        saveUserInfo(fullName, userNameString, email, password)
                    }
                    .addOnFailureListener {

                        *//* if (it.localizedMessage==st1){
                             util.createDialog(this,9)
                         }
                         if (it.localizedMessage==st2){
                             util.createDialog(this,10)
                         }
                         if (it.localizedMessage==st3){
                             util.createDialog(this,11)
                         }*//*
                        // util.logi("RegisterActivity  94  it.localizedMessage==> ${it.localizedMessage}")
                    }
            }
        }
    }*/

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    private fun userNameExist(): Boolean {
        var userN = binding.usernameRegister.text.toString()






        return false
    }


    fun downloadAllPost(): ArrayList<Post> {
        posts.clear()
        FirebaseFirestore.getInstance().collection(POST_REF).addSnapshotListener { value, error ->
            if (value != null) {
                for (doc in value.documents) {
                    var post = util.retrivePostFromFirestore(doc)
                    posts.add(post)
                }
            }
        }
        return posts
    }

    private fun saveUserInfo(fullName: String, userName: String, email: String, password: String) {
        // util.logi("SignUpActivity 120   userNameString=$userNameString   fullName=$fullName    email=$email  password=$password")
        val data = HashMap<String, Any>()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        data[USER_ID] = uid!!
        data[USER_FULLNAME] = fullName.lowercase(Locale.getDefault())
        data[USER_USERNAME] = userName.lowercase(Locale.getDefault())
        data[USER_EMAIL] = email
        data[USER_PASSWORD] = password
        data[USER_BIO] = "It's me man..."
        data[USER_TIME] = FieldValue.serverTimestamp()
        data[USER_IMAGE] =
            "https://firebasestorage.googleapis.com/v0/b/social55firestore.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=4a02bf76-8cc4-43e7-9750-930176c9c9ee"
        FirebaseFirestore.getInstance().collection(USER_REF).document(uid).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "החשבון שלך נוצר כעת ...", Toast.LENGTH_LONG).show()
                val pref = getSharedPreferences(SHAR_PREF, Context.MODE_PRIVATE).edit()
                pref.putString(CURRENT_USER_EXIST, EXIST)
                pref.apply()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error->${it.localizedMessage}", Toast.LENGTH_LONG).show()
                util.logi("RegisterActivity  122  it.localizedMessage==> ${it.localizedMessage}")

                FirebaseAuth.getInstance().signOut()

            }
    }

    private fun testBtn() {
        binding.aBtn.setOnClickListener {
            binding.usernameRegister.setText("Mr_a")
            binding.mailRegister.setText("a10@gimal.com")
            binding.passwordRegister.setText("111111")
        }
        binding.dBtn.setOnClickListener {
            binding.usernameRegister.setText("Mr_b")
            binding.mailRegister.setText("b10@gimal.com")
            binding.passwordRegister.setText("111111")
        }
        binding.cBtn.setOnClickListener {
            binding.usernameRegister.setText("Mr_c")
            binding.mailRegister.setText("c10@gimal.com")
            binding.passwordRegister.setText("111111")
        }
        binding.dBtn.setOnClickListener {
            binding.usernameRegister.setText("Mr_e")
            binding.mailRegister.setText("e10@gimal.com")
            binding.passwordRegister.setText("111111")
        }
        binding.eBtn.setOnClickListener {
            binding.usernameRegister.setText("Mr_f")
            binding.mailRegister.setText("f10@gimal.com")
            binding.passwordRegister.setText("111111")
        }
    }
}