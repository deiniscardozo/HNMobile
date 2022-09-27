package com.example.hnmobiletest.model.dataclass

import Author
import CommentText
import StoryTitle

data class HighlightResult(
    val author: Author,
    val comment_text: CommentText,
    val story_title: StoryTitle,
    val story_url:StoryUrl
)