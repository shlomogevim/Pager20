package com.sg.pager20.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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
import com.sg.pager20.databinding.ActivitySignUpBinding
import com.sg.pager20.utilities.*
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    val util= Utility()
    private lateinit var progressDialog: ProgressDialog
    var fullName=""
    var userName =""
    var email = ""
    var  password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
                binding.signinLinkBtn.setOnClickListener {
                    startActivity(Intent(this, SignInActivity::class.java))
                }
        binding.signupBtn.setOnClickListener {
            CreateAcounte()
        }
        testBtn()
    }
    private fun set_pre_data() {
        util.logi("SignUp  111")
        binding.fullnameSignup.setText("aa")
        binding.usernameSignup.setText("a")
        binding.emailSignup.setText("a1@gimal.com")
        binding.passwordSignup.setText("111111")
    }
    private fun CreateAcounte() {
        var headDialog=""
        var bodyDialog=""



        fullName = binding.fullnameSignup.text.toString()
        userName = binding.usernameSignup.text.toString()
        email = binding.emailSignup.text.toString()
        password = binding.passwordSignup.text.toString()

        /*  headDialog="מצטער ,"
          bodyDialog="כדי להכנס אתה צריך להירשם קודם ...  "
         createDialoge1(headDialog,bodyDialog)*/

        // util.logi("SignUpActivity 116   userNameString=$userNameString   fullName=$fullName    email=$email  password=$password")

        when {
            //TextUtils.isEmpty(fullName) ->{
            fullName.isEmpty() ->{
                bodyDialog="כדי להכנס אתה צריך להכניס שם מלא ...  "
                createDialoge1(headDialog,bodyDialog)
                // Toast.makeText(this, "Full name is empty", Toast.LENGTH_SHORT).show()
            }

            // TextUtils.isEmpty(userNameString) ->{
            userName.isEmpty() ->{
                bodyDialog="כדי להכנס אתה צריך להכניס שם משתמש ...  "
                createDialoge1(headDialog,bodyDialog)
                // Toast.makeText(this, "User name is empty", Toast.LENGTH_SHORT).show()
            }
            //TextUtils.isEmpty(email) ->{
            email.isEmpty() ->{
                bodyDialog="כדי להכנס אתה צריך להכניס כתובת מייל ...  "
                createDialoge1(headDialog,bodyDialog)

                // Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show()

            }
            // TextUtils.isEmpty(password) ->{
            password.isEmpty() ->{
                bodyDialog="כדי להכנס אתה צריך להכניס סיסמה של יותר מששה תווים ...  "
                createDialoge1(headDialog,bodyDialog)
                //Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
            }

            else -> {

                progressDialog = ProgressDialog(this)
                with(progressDialog) {
                    setTitle("SignUp")
                    setMessage("Please wait, this may take a while ...")
                    setCanceledOnTouchOutside(false)
                    show()
                }

                auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener { result ->
                        val changeRequest= UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .build()
                        result.user?.updateProfile(changeRequest)?.addOnFailureListener {
                            Toast.makeText(this, "Cannot update user account", Toast.LENGTH_LONG).show()
                        }
                        saveUserInfo(fullName, userName, email,password)
                    }
                    .addOnFailureListener {
                        util.logi(" failler ==> ${it.localizedMessage}")
                        Toast.makeText(this, "Cannot create User Account ...", Toast.LENGTH_LONG).show()
                        // util.logi("SignUp 112   ${it.localizedMessage}")

                    }


            }
        }
    }

    private fun createDialoge1(title:String,body:String) {
        /*var alertDialog = AlertDialog.Builder(context, R.style.MyDialogTheme)*/
        val alertDialog = AlertDialog.Builder(this, R.style.RoundedCornerDialog).create()

        alertDialog.setTitle(title)
        alertDialog.setMessage(body)

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "לחץ כאן כדי להמשיך ...",
            DialogInterface.OnClickListener {
                    dialog, which -> dialog.dismiss()
                // finish()
            })
        alertDialog.show()
    }


    private fun saveUserInfo(fullName: String, userName: String, email: String,password:String) {
        util.logi("SignUpActivity 120   userNameString=$userName   fullName=$fullName    email=$email  password=$password")

        val data=HashMap<String,Any>()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        data[USER_ID] = uid!!
        data[USER_FULLNAME] = fullName.lowercase(Locale.getDefault())
        data[USER_USERNAME] = userName.lowercase(Locale.getDefault())
        data[USER_EMAIL] = email
        data[USER_PASSWORD] = password
        data[USER_BIO] = "It's me man..."
        data[USER_TIME] = FieldValue.serverTimestamp()
        data[USER_IMAGE] = "https://firebasestorage.googleapis.com/v0/b/social55firestore.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=4a02bf76-8cc4-43e7-9750-930176c9c9ee"
        FirebaseFirestore.getInstance().collection(USER_REF).document(uid).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Account has been created ...", Toast.LENGTH_LONG).show()
                /* val intent = Intent(this, MainActivity::class.java)
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                 startActivity(intent)*/
                val pref = getSharedPreferences(SHAR_PREF, Context.MODE_PRIVATE).edit()
                pref.putString(CURRENT_USER_EXIST, EXIST)
                pref.apply()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error->${it.localizedMessage}", Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
    }

    private fun testBtn() {
        binding.aBtn.setOnClickListener {
            binding.usernameSignup.setText("Mr_a")
            binding.fullnameSignup.setText("Mr_aaaa")
            binding.emailSignup.setText("a10@gimal.com")
            binding.passwordSignup.setText( "111111")
        }
        binding.bBtn.setOnClickListener {
            binding.usernameSignup.setText("Mr_b")
            binding.fullnameSignup.setText("Mr_bbbb")
            binding.emailSignup.setText("b10@gimal.com")
            binding.passwordSignup.setText( "111111")
        }
        binding.cBtn.setOnClickListener {
            binding.usernameSignup.setText("Mr_c")
            binding.fullnameSignup.setText("Mr_cccc")
            binding.emailSignup.setText("c10@gimal.com")
            binding.passwordSignup.setText( "111111")
        }
        binding.dBtn.setOnClickListener {
            binding.usernameSignup.setText("Mr_e")
            binding.fullnameSignup.setText("Mr_eeee")
            binding.emailSignup.setText("e10@gimal.com")
            binding.passwordSignup.setText( "111111")
        }
        binding.eBtn.setOnClickListener {
            binding.usernameSignup.setText("Mr_f")
            binding.fullnameSignup.setText("Mr_ffff")
            binding.emailSignup.setText("f10@gimal.com")
            binding.passwordSignup.setText( "111111")
        }
    }



}