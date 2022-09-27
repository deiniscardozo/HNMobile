package com.example.hnmobiletest.model.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hits")
data class Hit(
    @PrimaryKey
    val objectID: String,
    val _highlightResult:HighlightResult,
    val _tags: List<String>,
    val author: String,
    val comment_text: String,
    val created_at: String,
    val created_at_i: Int,
    val num_comments: Any,
    val parent_id: Int,
    val points: Any,
    val story_id: Int,
    val story_text: Any,
    val story_title: String,
    val story_url: String,
    val title: Any,
    val url: Any
)