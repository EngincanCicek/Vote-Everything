package com.example.voteeverything.dao

import com.example.voteeverything.model.Comment
import com.example.voteeverything.model.Post
import com.example.voteeverything.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class DummyDao {

    private val db = FirebaseFirestore.getInstance()
    private val commentsCollection = db.collection("Comments")
    private val postsCollection = db.collection("Posts")
    private val usersCollection = db.collection("Users")


    fun createUser(user: User) {
        // Firestore'da belge oluştururken otomatik olarak bir kimlik (ID) atanır
        usersCollection
            .add(user) // Kullanıcıyı koleksiyona ekle
            .addOnSuccessListener { documentReference ->
                val userId = documentReference.id
                user.userId = userId // User objesine uid yi set et
                println("Kullanıcı başarıyla eklendi. ID: $userId")
            }
    }


    fun createComment(comment: Comment) {
        commentsCollection.document()
            .set(comment)
            .addOnSuccessListener { documentReference ->
                println("Yorum başarıyla eklendi ")
            }
            .addOnFailureListener { e ->
                println("Yorum eklenirken hata oluştu: ${e.message}")
            }
    }

    fun getAllComments(): Task<List<Comment>> {
        return commentsCollection.get()
            .continueWith { task ->
                val commentsList = mutableListOf<Comment>()
                val querySnapshot = task.result
                querySnapshot?.let {
                    for (document in it.documents) {
                        val comment = document.toObject(Comment::class.java)
                        comment?.let {
                            commentsList.add(it)
                        }
                    }
                }
                commentsList
            }
    }
    fun getUserPosts(userId: String?): Task<List<Post>> {
        val postsList = mutableListOf<Post>()

        // Firestore sorgusunu oluştur
        val query = postsCollection.whereEqualTo("userId", userId)

        // Sorguyu gerçekleştir ve Task'i döndür
        return query.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    querySnapshot?.let {
                        for (document in it.documents) {
                            val post = document.toObject(Post::class.java)
                            post?.let {
                                postsList.add(it)
                            }
                        }
                    }
                } else {
                    // Hata durumunu burada ele alabiliriz
                    // Örneğin: Loglama veya hata mesajı gösterme
                    task.exception?.let { e ->
                        println("getUserPosts Error: ${e.message}")
                    }
                }
            }
            .continueWith {
                postsList
            }
    }

    fun getAllPosts(): Task<List<Post>>? {
        val postsList: MutableList<Post> = ArrayList()
        return db.collection("Posts")
            .get()
            .continueWith<List<Post>> { task: Task<QuerySnapshot?> ->
                val querySnapshot = task.result
                if (querySnapshot != null) {
                    for (post in querySnapshot.toObjects(Post::class.java)) {
                        postsList.add(post)
                    }
                }
                postsList
            }
    }


}
