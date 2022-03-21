package com.sg.pager20.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.sg.pager20.R
import com.sg.pager20.adapters.PostAdapter
import com.sg.pager20.animation.BookFlipPageTransformer2
import com.sg.pager20.animation.CardFlipPageTransformer2
import com.sg.pager20.databinding.ActivityMainBinding
import com.sg.pager20.models.Post
import com.sg.pager20.utilities.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val util = Utility()
    val posts = ArrayList<Post>()
    lateinit var postAdapter: PostAdapter
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val pref = getSharedPreferences(SHAR_PREF, Context.MODE_PRIVATE).edit()
        pref.putString(CURRENT_USER_EXIST, NOT_EXIST)
        pref.apply()


        val posts = downloadAllPost()
        val pager = binding.viewPager
        postAdapter = PostAdapter(pager, this, posts)
        pager.adapter = postAdapter
        addAnimation(pager)


    }

    fun downloadAllPost(): ArrayList<Post> {
        posts.clear()
        FirebaseFirestore.getInstance().collection(POST_REF).addSnapshotListener { value, error ->
            if (value != null) {
                for (doc in value.documents) {
                    var post = util.retrivePostFromFirestore(doc)
                    posts.add(post)
                }
                postAdapter.notifyDataSetChanged()
            }
        }
        return posts
    }

    private fun addAnimation(pager: ViewPager2) {
        val book = BookFlipPageTransformer2()
        book.setEnableScale(true)
        book.setScaleAmountPercent(90f)
        pager.setPageTransformer(book)

        val card = CardFlipPageTransformer2()
        card.setScalable(false)
        pager.setPageTransformer(card)
    }


    private fun to_Automate_Scrolling_addThisInto_onCreate(pager: ViewPager2) {
        lateinit var sliderHandler: Handler
        lateinit var sliderRun: Runnable

        pager.clipToPadding = false
        pager.clipChildren = false
        pager.offscreenPageLimit = 3
        pager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val comPostPageTarnn = CompositePageTransformer()
        comPostPageTarnn.addTransformer(MarginPageTransformer(40))
        comPostPageTarnn.addTransformer { page, position ->
            val r: Float = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        pager.setPageTransformer(comPostPageTarnn)
        sliderHandler = Handler()
        sliderRun = Runnable {
            pager.currentItem = pager.currentItem + 1
        }
        pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRun)
                    sliderHandler.postDelayed(sliderRun, 2000)
                }
            })
    }
}
