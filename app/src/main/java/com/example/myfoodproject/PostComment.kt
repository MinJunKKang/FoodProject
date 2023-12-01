package com.example.myfoodproject

data class Post(val postId: String, val content: String)
data class Comment(val commentId: String, val postId: String, val content: String)