package com.sg.pager20.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sg.pager20.R
import com.sg.pager20.databinding.ActivitySignInBinding
import com.sg.pager20.utilities.CURRENT_USER_EXIST
import com.sg.pager20.utilities.EXIST
import com.sg.pager20.utilities.SHAR_PREF

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupLinkBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {
            loginUser()
        }


        testBtn()
    }

    private fun createDialoge1(title: String, body: String) {
        /*var alertDialog = AlertDialog.Builder(context, R.style.MyDialogTheme)*/
        val alertDialog = AlertDialog.Builder(this, R.style.RoundedCornerDialog).create()

        alertDialog.setTitle(title)
        alertDialog.setMessage(body)

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "לחץ כאן כדי להמשיך ...",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                finish()
            })
        alertDialog.show()
    }


    private fun createDialoge() {
        /*var alertDialog = AlertDialog.Builder(context, R.style.MyDialogTheme)*/
        val alertDialog = AlertDialog.Builder(this, R.style.RoundedCornerDialog).create()

        // alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#AE6118")))

        alertDialog.setTitle("מצטער ,")
        alertDialog.setMessage("כדי להכנס אתה צריך להירשם קודם ...  ")

        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                finish()
            })
        alertDialog.show()
    }

    private fun loginUser() {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()
        when {

            TextUtils.isEmpty(email) ->
                Toast.makeText(this, " הכנס כתובת מייל ...", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(password) ->
                Toast.makeText(this, "הכנס סיסמא של 6 תווים לפחות ...", Toast.LENGTH_SHORT).show()
            else -> {
                val progressDiallog = ProgressDialog(this)
                with(progressDiallog) {
                    setTitle("Login ....")
                    setMessage("Please wait, this may take a while ...")
                    setCanceledOnTouchOutside(false)
                    show()
                }
                val mAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDiallog.dismiss()
                        val pref = getSharedPreferences(SHAR_PREF, Context.MODE_PRIVATE).edit()
                        pref.putString(CURRENT_USER_EXIST, EXIST)
                        pref.apply()
                        finish()
                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Errore :$message", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        progressDiallog.dismiss()
                    }
                }
            }
        }
    }


    private fun testBtn() {
        binding.aBtn.setOnClickListener {
            binding.emailLogin.setText("a10@gimal.com")
            binding.passwordLogin.setText("111111")
            //   signInBtn(1)
        }
        binding.bBtn.setOnClickListener {
            binding.emailLogin.setText("b10@gimal.com")
            binding.passwordLogin.setText("111111")
            //  signInBtn(2)
        }
        binding.cBtn.setOnClickListener {
            binding.emailLogin.setText("c10@gimal.com")
            binding.passwordLogin.setText("111111")
            // signInBtn(3)
        }
        binding.dBtn.setOnClickListener {
            binding.emailLogin.setText("e10@gimal.com")
            binding.passwordLogin.setText("111111")
            //  signInBtn(4)
        }
        binding.eBtn.setOnClickListener {
            binding.emailLogin.setText("f10@gimal.com")
            binding.passwordLogin.setText("111111")
            //signInBtn(5)
        }
    }

    private fun signInBtn(index: Int) {
        var email = ""
        var password = "111111"
        when (index) {
            1 -> email = "a@gmal.com"
            2 -> email = "b@gmal.com"
            3 -> email = "c@gmal.com"
            4 -> email = "d@gmal.com"
            5 -> email = "e@gmal.com"
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }


    }
}