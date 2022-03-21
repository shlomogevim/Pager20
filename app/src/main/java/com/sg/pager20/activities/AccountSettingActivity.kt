package com.sg.pager20.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.sg.pager20.R
import com.sg.pager20.databinding.ActivityAccountSettingBinding
import com.sg.pager20.utilities.*
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage

class AccountSettingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountSettingBinding
    var checker = ""
    var imageUri: Uri? = null
    var myUrl = ""

    private var storageProfilePicRef: StorageReference? = null
    private var currentUser: FirebaseUser?=null
    private lateinit var progressDialog: ProgressDialog
    private val util = Utility()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAccountSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentUser = FirebaseAuth.getInstance().currentUser
        util.logi("AcountSettingActivity 115     currentUser=$currentUser")
        if (currentUser==null){
            createDialoge()
        }else {

            storageProfilePicRef = FirebaseStorage.getInstance().reference.child("Profile Picture")
            progressDialog = ProgressDialog(this)

            binding.logoutBtn.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, SignInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            binding.changeImageTextBtn.setOnClickListener {
                checker = "clicked"
                CropImage.activity()
                    .setAspectRatio(1,1)
                    .start(this)
            }
            binding.saveInforProfileBtn.setOnClickListener {
                if (checker == "clicked") {
                    uploadImageAndUpdaeInfo()
                } else {
                    uploadUserInfoOnly()
                }
            }
            userInfo()
        }
    }

    private fun createDialoge() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this,R.style.RoundedCornerDialog).create()

        //  alertDialog.window?.setBackgroundDrawable( ColorDrawable(Color.parseColor("#AE6118")))

        alertDialog.setTitle(" סליחות,")
        alertDialog.setMessage("אבל אתה צריך להירשם כדי שיהיה לך פרופיל כלשהוא בעולם הזה ... ")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK",
            DialogInterface.OnClickListener {
                    dialog, which -> dialog.dismiss()
                finish()
            })
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = CropImage.getActivityResult(data)
            imageUri=result.uri
            binding.profileImage.setImageURI(imageUri)
        }
    }
    fun sendToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show()
    }

    private fun uploadUserInfoOnly() {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid
        when {
            TextUtils.isEmpty(binding.fullNameProfileFragment.text.toString()) ->
                sendToast("Please write full name.")
            binding.usernameProfileFrag.toString() == "" ->
                sendToast("Please write user name first.")
            binding.bioProfileFragment.text.toString() == "" ->
                sendToast("Please your bio...")
            else -> {

                val data = HashMap<String, Any>()
                data[USER_FULLNAME] = binding.fullNameProfileFragment.text.toString().toLowerCase()
                data[USER_USERNAME] = binding.usernameProfileFrag.text.toString().toLowerCase()
                data[USER_BIO] = binding.bioProfileFragment.text.toString().toLowerCase()

                if (currentUid != null) {
                    FirebaseFirestore.getInstance().collection(USER_REF).document(currentUid)
                        .update(data)
                        .addOnSuccessListener {
                            sendToast("Account information has been update successfully ...")
                            // startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                }
            }
        }
    }
    private fun uploadImageAndUpdaeInfo() {
        //util.logi("AcountSettingActivity imgeUri=$imageUri")

        when {
            imageUri == null ->
                sendToast("Please select image first.")
            TextUtils.isEmpty(binding.fullNameProfileFragment.text.toString()) ->
                sendToast("Please write full name.")
            binding.usernameProfileFrag.toString() == "" ->
                sendToast("Please write user name first.")
            binding.bioProfileFragment.text.toString() == "" ->
                sendToast("Please your bio...")
            else -> {
                progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Account Setting")
                progressDialog.setMessage("Please wait, we are updating your profile...")
                progressDialog.show()

                val fileRef = storageProfilePicRef?.child(currentUser?.uid + "jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef!!.putFile(imageUri!!)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            progressDialog.dismiss()
                            throw it
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val data = HashMap<String, Any>()
                        data[USER_FULLNAME] =
                            binding.fullNameProfileFragment.text.toString().toLowerCase()
                        data[USER_USERNAME] =
                            binding.usernameProfileFrag.text.toString().toLowerCase()
                        data[USER_BIO] =
                            binding.bioProfileFragment.text.toString().toLowerCase()
                        data[USER_IMAGE] = myUrl
                        currentUser?.let {
                            FirebaseFirestore.getInstance().collection(USER_REF)
                                .document(it.uid).update(data)
                                .addOnSuccessListener {
                                    sendToast("Account information has been update successfully ...")
                                    // startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                    progressDialog.dismiss()
                                }
                        }

                    } else {
                        progressDialog.dismiss()
                    }
                })
            }
        }
    }
    private fun userInfo() {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            FirebaseFirestore.getInstance().collection(USER_REF).document(currentUid).get()
                .addOnSuccessListener {
                    val user = util.convertToUser(it)
                    Picasso.get().load(user.profileImage).placeholder(R.drawable.profile)
                        .into(binding.profileImage)
                    binding.fullNameProfileFragment.setText(user.fullName)
                    binding.usernameProfileFrag.setText(user.userName)
                    binding.bioProfileFragment.setText(user.dio)
                }.addOnFailureListener {
                    //Log.d("fff", "Fail ->${it.localizedMessage}")
                }
        }
    }
}
