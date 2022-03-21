package com.sg.pager20.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.pager20.R
import com.sg.pager20.databinding.ActivityUpdateCommentBinding
import com.sg.pager20.utilities.*

class UpdateCommentActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateCommentBinding
    var commentPostId=""
    var commentId=""
    var commentText=""
    val util= Utility()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUpdateCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        commentId=intent.getStringExtra(COMMENT_ID).toString()
        commentPostId=intent.getStringExtra(COMMENT_POST_ID).toString()
        commentText=intent.getStringExtra(COMMENT_TEXT).toString()
        binding.updateCommentTxt.setText(commentText)
        binding.updateCommentBtn.setOnClickListener {
            //  util.logi("UpdateCommentActivity 111   commentId=$commentId   commentPostId=$commentPostId ")
            FirebaseFirestore.getInstance().collection(COMMENT_REF).document(commentPostId)
                .collection(COMMENT_LIST).document(commentId)
                .update(COMMENT_TEXT,binding.updateCommentTxt.text.toString())
                .addOnSuccessListener {
                    hideKeyboard()
                    finish()
                }
                .addOnFailureListener {
                    //    util.logi("UpdateCommentActivity 112   commentId=$commentId   commentPostId=$commentPostId ")
                }
        }
    }
    private fun hideKeyboard() {
        val inputeManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputeManager.isAcceptingText) {
            inputeManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }
}