package com.sg.pager20.interfaces

import com.sg.pager20.models.Comment


interface CommentsOptionClickListener {
    fun optionMenuClicked(comment: Comment)
}