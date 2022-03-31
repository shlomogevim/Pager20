package com.sg.pager20.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sg.pager20.R
import com.sg.pager20.databinding.ActivityLoginBinding
import com.sg.pager20.utilities.CURRENT_USER_EXIST
import com.sg.pager20.utilities.EXIST
import com.sg.pager20.utilities.SHAR_PREF
import com.sg.pager20.utilities.Utility

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    val util=Utility()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnLogRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left)
        }
        binding.loginBtn.setOnClickListener {
            loginUser()
        }
        testBtn()
    }

    private fun loginUser() {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()
        when {
            TextUtils.isEmpty(email) ->
                util.createDialog(this,4)
            TextUtils.isEmpty(password) ->
                util.createDialog(this,5)
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
}