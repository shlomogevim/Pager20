package com.sg.pager20.models

data class Post(
    var postId:String="",
    var postNum:Int=1,
    var lineNum: Int =1,
    var imageUri:String="",
    var postText: ArrayList<String> = arrayListOf<String>(),
    var postMargin: ArrayList<ArrayList<Int>> =arrayListOf<ArrayList<Int>>(),
    var postBackground:String="",
    var postTransparency:Int=0,
    var postTextSize:ArrayList<Int> = arrayListOf<Int>(),
    var postPadding:ArrayList<Int> = arrayListOf<Int>(),
    var postTextColor:ArrayList<String> = arrayListOf<String>(),
    var postFontFamily:Int=0,
    var postRadiuas:Int=0
)
