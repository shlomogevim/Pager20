package com.sg.pager20.utilities

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import coil.load

class UtilityPro( val context: Context) {

    val familyHelper=FontFamilies()
   var  lineNum=2
    var backGround=""
    var tran = 0
    lateinit var strings: Array<String>
   lateinit var margin: Array<Array<Int>>
    lateinit var padding: Array<Int>
    lateinit var textSizeArray: Array<Int>
    lateinit var textColorArray: Array<String>
    var fontFamily= 0
    var radius= 0
    val textView1=TextView(context)
    val textView2=TextView(context)
    val textView3=TextView(context)


    fun Int.toPx():Int=(this* Resources.getSystem().displayMetrics.density).toInt()


    fun addTextView(  layout: ConstraintLayout, textArr: Array<String>,ind: Int) {
        getParameter(ind)
        val tra=familyHelper.getTransfo(tran)
        val shape = GradientDrawable()
        shape.cornerRadius = radius.toPx().toFloat()
        shape.setColor(Color.parseColor("#$tra$backGround"))
        textView1.text=strings[0]
        textView1.textSize=textSizeArray[1].toFloat()
        if (textColorArray[0]== CONSTANT){
            textView1.setTextColor(Color.parseColor(textColorArray[1]))
        } else{
            textView1.setTextColor(Color.parseColor(textColorArray[1]))
        }
        textView1.id= View.generateViewId()
        textView1.background=shape
        textView1.typeface=ResourcesCompat.getFont(context,fontFamily)
        textView1.setPadding(
            padding[0].toPx(),
            padding[1].toPx(),
            padding[2].toPx(),
            padding[3].toPx(),
        )
        textView1.gravity= Gravity.CENTER
        if (lineNum>1){
            textView2.text=strings[1]
            if (textSizeArray[0]==0){
                textView2.textSize=textSizeArray[1].toFloat()
            }else{
                textView2.textSize=textSizeArray[2].toFloat()
            }
            if (textColorArray[0]== CONSTANT){
                textView2.setTextColor(Color.parseColor(textColorArray[1]))
            } else{
                textView2.setTextColor(Color.parseColor(textColorArray[2]))
            }
            textView2.id= View.generateViewId()
            textView2.background=shape
            textView2.typeface=ResourcesCompat.getFont(context,fontFamily)
            textView2.setPadding(
                padding[0].toPx(),
                padding[1].toPx(),
                padding[2].toPx(),
                padding[3].toPx(),
            )
            textView2.gravity= Gravity.CENTER
        }



        val lp1=RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        textView1.layoutParams=lp1
        if (lineNum>1){
            val lp2=RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            textView2.layoutParams=lp2
        }

        layout.addView(textView1)
        if (lineNum>1){
            layout.addView(textView2)
        }

        val containSet=ConstraintSet()
        containSet.clone(layout)

        if (margin[0][0] > -1) {
            containSet.connect(
                textView1.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT, margin[0][0].toPx()
            )
        }
        if (margin[0][1] > -1) {
            containSet.connect(
                textView1.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP, margin[0][1].toPx()
            )
        }
        if (margin[0][2] > -1) {
            containSet.connect(
                textView1.id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT, margin[0][2].toPx()
            )
        }
        if (margin[0][3] > -1) {
            containSet.connect(
                textView1.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM, margin[0][3].toPx()
            )
        }

        if (lineNum>1) {
            if (margin[1][0] > -1) {
                containSet.connect(
                    textView2.id,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT, margin[1][0].toPx()
                )
            }
            if (margin[1][1] > -1) {
                containSet.connect(
                    textView2.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP, margin[1][1].toPx()
                )
            }
            if (margin[1][2] > -1) {
                containSet.connect(
                    textView2.id,
                    ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT, margin[1][2].toPx()
                )
            }
            if (margin[1][3] >-1) {
                containSet.connect(
                    textView2.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.BOTTOM, margin[1][3].toPx()
                )
            }
        }

        containSet.applyTo(layout)
    }



    private fun getParameter(ind: Int) {
        if (ind==1){
        //   image.load("https://cdn.pixabay.com/photo/2015/08/26/11/06/people-talking-908342_1280.jpg")
             backGround = "263238"
            tran = 4
            strings = arrayOf(
                "כל אחד מדבר את מה שהוא."
            )
            margin = arrayOf(arrayOf(0, 0, 0, -1))
            padding= arrayOf(10, 0, 10, 0)
            textSizeArray = arrayOf(1, 30)
            textColorArray = arrayOf(CONSTANT,  "#f6ff03")
            fontFamily=200
            radius=15
        }
    }



 /*   fun addTextView(context: Context, title: String, body:String, ind:Int){
        val tvTitle= TextView(context)
        val tvBody= TextView(context)

        getParameter(ind)
        tvTitle.text=title
        tvBody.text=body
    }*/
}