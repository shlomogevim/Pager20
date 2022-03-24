package com.sg.alma12.Posts.general

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.sg.pager20.R
import com.sg.pager20.adapters.DrawPostCenter
import com.sg.pager20.models.Post
import com.sg.pager20.utilities.CONSTANT
import com.sg.pager20.utilities.Utility


class Post4Lines(val context: Context) {
    private val util = Utility()
    private val layout: ConstraintLayout = (context as Activity).findViewById(R.id.messageLayout)
    val drawPost = DrawPostCenter(context)






    fun loadPostComment() {

        util.logi(" post4lines 121")



      //  layout.setBackgroundColor(Color.RED)


        val post = Post()
        with(post) {
            postNum = 427
            lineNum = 4
            imageUri = "https://cdn.pixabay.com/photo/2015/07/09/22/45/tree-838667_1280.jpg"
            postText = arrayListOf(
                "נחמה",
                "זה שמישהו אומר לך שאתה בסדר,",
                "שלווה",
                "זה שאתה מבין שאתה בסדר."
            )
            val di =10
            val dd =0
            postMargin = arrayListOf(
                arrayListOf(0, 5 + di, 0, -1 + dd),
                arrayListOf(0, 35 + di, 0, -1 + dd),
                arrayListOf(0, 100 + di, 0, -1 + dd),
                arrayListOf(0, 130 + di, 0, -1 + dd)
            )

            postTransparency =0
            postTextSize = arrayListOf(0, 20)
            postBackground = "1E4174"
            val textColor = "DDA94B"
            postFontFamily =300

            val col = "#$textColor"
            postTextColor = arrayListOf(CONSTANT, col)
            postPadding = arrayListOf(10, 0, 10, 0)
            postRadiuas = 15

        }
        layout.visibility= View.VISIBLE
     drawPost.drawPostComment(post, layout)
      //  util.sendPostToStringFirestore(post)
    }
}




