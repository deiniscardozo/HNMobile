package com.example.hnmobiletest.model.dataclass

data class StoryUrl(
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
)