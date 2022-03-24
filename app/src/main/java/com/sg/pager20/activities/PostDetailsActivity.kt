package com.sg.pager20.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sg.alma12.Posts.general.Post4Lines
import com.sg.pager20.R
import com.sg.pager20.adapters.CommentAdapter
import com.sg.pager20.databinding.ActivityPostDetailsBinding
import com.sg.pager20.interfaces.CommentsOptionClickListener
import com.sg.pager20.models.Comment
import com.sg.pager20.models.Post
import com.sg.pager20.utilities.*
import java.util.ArrayList

class PostDetailsActivity : AppCompatActivity(), CommentsOptionClickListener {
    lateinit var binding: ActivityPostDetailsBinding
    private var currentUser: FirebaseUser? = null
    var util = Utility()

    // val utilPro=UtilityPro(this)
    var textViewArray = ArrayList<TextView>()

    lateinit var commentsRV: RecyclerView
    lateinit var commentAdapter: CommentAdapter
    val comments = ArrayList<Comment>()
    var currentPostNum = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailsBinding.inflate(layoutInflater)
        //util.logi("PostDetailAcivity 116   ")
        createTextViewArray()
        setContentView(binding.root)
        currentPostNum = intent.getIntExtra(DETAIL_POST_EXSTRA, 0)

        create_commentsRv()
        operateButtoms()
        createComments()
        findCurrentPost()

    }

    private fun operateButtoms() {
        binding.signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.signInBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, AccountSettingActivity::class.java))
        }
        binding.profileImageComment.setOnClickListener {
            addComment()
        }
    }

    override fun onStart() {
        super.onStart()
        //   util.logi("PostDetails 121 in OnStart")
        val pref = getSharedPreferences(SHAR_PREF, Context.MODE_PRIVATE)
        val existUser = pref.getString(CURRENT_USER_EXIST, "none").toString()
        // util.logi("PostDetails 122 in OnStart          existUse1=$existUser")
        if (existUser == EXIST) {
            currentUser = FirebaseAuth.getInstance().currentUser
            binding.nameCurrentUserName.setText(currentUser!!.displayName.toString())
        }
    }

    private fun createComments() {
        FirebaseFirestore.getInstance().collection(COMMENT_REF).document(currentPostNum.toString())
            .collection(COMMENT_LIST)
            .orderBy(COMMEND_TIME_STAMP, Query.Direction.ASCENDING)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    comments.clear()
                    for (doc in value.documents) {
                        val comment = util.retriveCommentFromFirestore(doc)
                        comments.add(comment)
                    }
                    //    util.logi("PostDetailsActivity 111        comments.size=${comments.size} ")
                    commentAdapter.notifyDataSetChanged()
                }

            }

    }

    private fun addComment() {
        //util.logi("PostDetails  112                               currentUser=$currentUser")
        if (currentUser == null) {
              util.createDialog(this, 1)
        } else {
            val commentText = binding.postCommentText.text.toString()
            if (commentText == "") {
                util.toasti(this, " היי , קודם תכתוב משהו בהערה ואחר כך תלחץ ...")
            } else {
                binding.postCommentText.text.clear()
                hideKeyboard()
                FirebaseFirestore.getInstance().collection(POST_REF)
                    .document(currentPostNum.toString())
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val post = util.retrivePostFromFirestore(task.result)
                            util.createComment(post, commentText)
                        }
                    }
            }
        }
    }



    private fun findCurrentPost() {
        FirebaseFirestore.getInstance().collection(POST_REF).document(currentPostNum.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val post = util.retrivePostFromFirestore(task.result)
                    drawHeadline(post)
                }
            }
    }


    private fun drawHeadline(post: Post) {
        val num = post.postNum
        val st = "   פוסט מספר: " + "$num   "
        binding.postNumber.text = st
        // util.logi("PostDetailsActivity  111  post=$post     \n post.postText.size= ${post.postText.size}")
        for (ind in 0 until post.postText.size) {

            //   util.logi("PostDetailsActivity  112  ind=$ind     \n")

            textViewArray[ind].visibility = View.VISIBLE
            textViewArray[ind].text = post.postText[ind]
        }
    }

    private fun create_commentsRv() {
        commentsRV = binding.rvPost
        commentAdapter = CommentAdapter(comments, this)
        val layoutManger = LinearLayoutManager(this)
        layoutManger.reverseLayout = true
        commentsRV.layoutManager = layoutManger
        commentsRV.adapter = commentAdapter
    }


    private fun createTextViewArray() {
        with(binding) {
            textViewArray = arrayListOf(
                tvPost1,
                tvPost2,
                tvPost3,
                tvPost4,
                tvPost5,
                tvPost6,
                tvPost7,
                tvPost8,
                tvPost9
            )
        }

    }

    private fun hideKeyboard() {
        val inputeManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputeManager.isAcceptingText) {
            inputeManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    override fun optionMenuClicked(comment: Comment) {

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.option_menu, null)
        val deleteBtn = dialogView.findViewById<Button>(R.id.optionDelelBtn)
        val editBtn = dialogView.findViewById<Button>(R.id.optionEditBtn)
        builder.setView(dialogView)
            .setNegativeButton("Cancel") { _, _ -> }
        val ad = builder.show()
        deleteBtn.setOnClickListener {
            util.deleteComment(comment)
            finish()
        }
        editBtn.setOnClickListener {
            val intent = Intent(this, UpdateCommentActivity::class.java)
            intent.putExtra(COMMENT_POST_ID, comment.postId)
            intent.putExtra(COMMENT_ID, comment.commntId)
            intent.putExtra(COMMENT_TEXT, comment.text)
            startActivity(intent)
            finish()
        }
    }


}


/*  private fun secondDialog() {
       val builder = AlertDialog.Builder(this)
       val dialogView = layoutInflater.inflate(R.layout.option_menu1, null)
       val btn1 = dialogView.findViewById<Button>(R.id.btn1_OptionMenu1)
       val btn2 = dialogView.findViewById<Button>(R.id.btn2_OptionMenu1)
       val loti = dialogView.findViewById<LottieAnimationView>(R.id.lottie_anim)

       builder.setView(dialogView)
           .setNegativeButton("המשך ...") { _, _ -> }
       val ad = builder.show()
       btn1.setOnClickListener {
           loti.setAnimation("right.json")
           //util.deleteComment(comment)
           // finish()
           //    ad.dismiss()
       }
       btn2.setOnClickListener {
           loti.setAnimation("rocket.json")
            }

   }*/


/*  override fun onStart() {
     super.onStart()
     if (FirebaseAuth.getInstance().currentUser != null) {
         val intent = Intent(this, MainActivity::class.java)
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
         startActivity(intent)
         finish()
     }
 }*/


/*  private fun firstDeloge() {

      val textArr= arrayOf(
          "מצטער משתמש אנונימי",
          "כדי לכתוב הערה אתה קודם צריך להירשם. "
      )
      val di=0
      val marginA = arrayOf(
          arrayOf(0, 0+di, 0, -1),
          arrayOf(0, 50+di, 0, -1)
      )
      val layout=binding.messageLayout
      layout.visibility=View.VISIBLE
      utilPro.addTextView(layout,textArr,1)
      //utilPro.addTextView1(layout,textArr,1)
  }*/