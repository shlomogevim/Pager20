package com.sg.pager20.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    val util = Utility()
    val posts = ArrayList<Post>()
    var fullName = ""
    var userName = ""
    var email = ""
    var password = ""
    private val coroutinScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        demiData()

        binding.btnRegister.setOnClickListener {
            onBackPressed()
        }
        binding.saveRegisterBtn.setOnClickListener {

           newUserRegistration()

        }

        testBtn()
    }

    private fun demiData() {
        binding.usernameRegister.setText("Papi1")
        binding.mailRegister.setText("a@papi1.gmail")
        binding.passwordRegister.setText("111111")
    }

    fun  newUserRegistration(){

        userName = binding.usernameRegister.text.toString()
        email = binding.mailRegister.text.toString()
        password = binding.passwordRegister.text.toString()

        val boTextviewEmpty=false
        val boUserExsist=false

        coroutinScope.launch {
            val chkIfEmptyTextViewDeffer=coroutinScope.async(Dispatchers.Default ) { chkIfTextViewEmpty1(boTextviewEmpty)   }
            val chkIfEmpty=chkIfEmptyTextViewDeffer.await()
            if (!chkIfEmpty){
                val chkIfUserExistDeffer=coroutinScope.async(Dispatchers.Default) { chkIfUserNameExist1(boUserExsist) }
                val chkIfUserExistOriginal=chkIfUserExistDeffer.await()
                if (!chkIfUserExistOriginal){

                }
            }


        }
        var boT = false
        var boL = true
        var boUser = true

        //   util.logi("RegisterActivity 60 ")


        boL = chkIfTextViewEmpty(boL)
        boUser = chkIfUserNameExist(boUser)
        if (boL && !boUser) {
            boT = true
            var boT1 = true
            creatNewUser(boT1)
        }

    }
    private suspend fun  chkIfTextViewEmpty1(boL: Boolean): Boolean {
        var bol = boL

        when {
            userName.isEmpty() -> {
                util.createDialog(this, 6)
                bol = false
            }
            email.isEmpty() -> {
                util.createDialog(this, 7)
                bol = false
            }
            password.isEmpty() -> {
                util.createDialog(this, 8)
                bol = false
            }
        }

        util.logi("RegisterActivity 92 inside  chkIfTextViewEmpty      =======>   bol=$bol ")
        return bol
    }
    private fun chkIfUserNameExist1(bo: Boolean): Boolean {
        // util.logi("RegisterActivity 92 ")
        var userN = binding.usernameRegister.text.toString()
        var boIn = bo
        boIn = false
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
        util.logi("RegisterActivity 11      inside   chkIfUserNameExist ==============>  boln=$boIn ")
        /* if (boIn) {
             util.createDialog(this, 12)
         }*/
        return boIn
    }
    private  fun  chkIfTextViewEmpty(boL: Boolean): Boolean {
        var bol = boL

        when {
            userName.isEmpty() -> {
                util.createDialog(this, 6)
                bol = false
            }
            email.isEmpty() -> {
                util.createDialog(this, 7)
                bol = false
            }
            password.isEmpty() -> {
                util.createDialog(this, 8)
                bol = false
            }
        }

        util.logi("RegisterActivity 92 inside  chkIfTextViewEmpty      =======>   bol=$bol ")
        return bol
    }

    private fun chkIfUserNameExist(bo: Boolean): Boolean {
       // util.logi("RegisterActivity 92 ")
        var userN = binding.usernameRegister.text.toString()
        var boIn = bo
        boIn = false
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
        util.logi("RegisterActivity 11      inside   chkIfUserNameExist ==============>  boln=$boIn ")
       /* if (boIn) {
            util.createDialog(this, 12)
        }*/
        return boIn
    }


    private fun creatNewUser(boU:Boolean):Boolean {
        var boIn=boU
        boIn=true
       // util.logi("RegisterActivity 110    email=$email            password=$password ")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val changeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .build()
                result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                 //   util.logi("RegisterActivity  94  failler ==> ${it.localizedMessage}")
                }
                saveUserInfo(fullName, userName, email, password)
            }.addOnFailureListener {
                util.logi("RegisterActivity  133   inside createNewUser  ==> ${it.localizedMessage}")
                val st1 = "The email address is already in use by another account."
                val st2 =
                    "The given password is invalid. [ Password should be at least 6 characters ]"
                val st3 = "The email address is badly formatted."
                util.logi("RegisterActivity  135  inside  creatNewUser  ==> ${it.localizedMessage}")
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
        util.logi("RegisterActivity  150   inside createNewUser      boIn=$boIn}")
        return boIn
    }

    private fun createAccountPlus() {
        var demiBo = false
        val existU = chkUserNamePlus(demiBo)
        if (existU) {
            util.createDialog(this, 12)
        } else {
            createRealAcounte()
        }
    }

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

    private fun createAccount() {
        var arr = arrayListOf<Int>(1, 2)
        val arr1: ArrayList<Int> = chkUserName(arr)
        if (arr1[1] == 1) {
            util.createDialog(this, 12)
        } else {
            createRealAcounte()
        }
    }


    private fun boUser(boU: Boolean): Boolean {
        val bol = boU





        return bol
    }


    private fun createRealAcounte() {
        userName = binding.usernameRegister.text.toString()
        email = binding.mailRegister.text.toString()
        password = binding.passwordRegister.text.toString()
        var bo = false
        when {

            userName.isEmpty() -> {
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
                            .setDisplayName(userName)
                            .build()
                        result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                            util.logi("RegisterActivity  94  failler ==> ${it.localizedMessage}")
                        }
                        saveUserInfo(fullName, userName, email, password)
                    }
                    .addOnFailureListener {

                        /* if (it.localizedMessage==st1){
                             util.createDialog(this,9)
                         }
                         if (it.localizedMessage==st2){
                             util.createDialog(this,10)
                         }
                         if (it.localizedMessage==st3){
                             util.createDialog(this,11)
                         }*/
                        // util.logi("RegisterActivity  94  it.localizedMessage==> ${it.localizedMessage}")
                    }
            }
        }
    }

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
        // util.logi("SignUpActivity 120   userName=$userName   fullName=$fullName    email=$email  password=$password")
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