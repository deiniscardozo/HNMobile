package com.example.hnmobiletest

data class StoryUrl(
    val fullyHighlighted: Boolean,
    val matchLevel: String,
    val matchedWords: List<String>,
    val value: String
)