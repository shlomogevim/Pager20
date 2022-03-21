package com.sg.pager20.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sg.pager20.R
import com.sg.pager20.activities.PostDetailsActivity
import com.sg.pager20.models.Post
import com.sg.pager20.utilities.DETAIL_POST_EXSTRA
import com.sg.pager20.utilities.Utility
import kotlin.collections.ArrayList

//class PostAdapter(context:Context, val posts:ArrayList<Post>): RecyclerView.Adapter<PostAdapter.PagerViewHolder>(){
class PostAdapter(val viewPager: ViewPager2, val context: Context, val posts: ArrayList<Post>) :
    RecyclerView.Adapter<PostAdapter.PagerViewHolder>() {

  val drawPost = DrawPostCenter(context)


    val util = Utility()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PagerViewHolder(view)
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindImage(posts[position])

        //---------------------
        if (position == posts.size - 2) {
            viewPager.post(run)
        }
        //------------------
    }


    override fun getItemCount() = posts.size

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImage = itemView?.findViewById<ImageView>(R.id.pagerImage)

        fun bindImage(post: Post) {
            val layout = itemView?.findViewById<ConstraintLayout>(R.id.itemLayout)
            drawPost.drawPost(post, layout)

            postImage.setOnClickListener {
                val intent = Intent(context, PostDetailsActivity::class.java)
                intent.putExtra(DETAIL_POST_EXSTRA, post.postNum)
                context.startActivities(arrayOf(intent))
            }
        }

    }


    //-------------------
    val run = object : Runnable {            // for automate scrolling
        override fun run() {
            posts.addAll(posts)
            notifyDataSetChanged()
        }
    }
    //-------------
}

