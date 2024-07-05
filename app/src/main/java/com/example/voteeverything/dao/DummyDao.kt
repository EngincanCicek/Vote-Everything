package com.example.voteeverything.dao

import com.example.voteeverything.model.Comment
import com.example.voteeverything.model.Post
import com.example.voteeverything.model.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.UUID


class DummyDao {

    private val db = FirebaseFirestore.getInstance()
    private val commentsCollection = db.collection("Comments")
    private val postsCollection = db.collection("Posts")
    private val usersCollection = db.collection("Users")
    private val auth = FirebaseAuth.getInstance()


    fun createUser(user: User): Unit {
        // Create a reference to the "Users" collection
        val usersCollection = db.collection("Users")

        // Set the userId field in the User object and use it as the document ID
        user.userId = UUID.randomUUID().toString()
        val documentId = user.userId

        // Add the User object with the userId as the document ID
        usersCollection.document(documentId).set(user)
            .addOnSuccessListener {
                println("Kullanıcı başarıyla eklendi. ID: $documentId")
            }
            .addOnFailureListener { exception ->
                // Handle errors appropriately, e.g., show a user-friendly message
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

    fun getCommentsForPost(postId: String): Task<List<Comment>> {
        val commentsList = mutableListOf<Comment>()

        // Firestore sorgusunu oluştur
        val query = commentsCollection.whereEqualTo("postId", postId)

        // Sorguyu gerçekleştir ve Task'i döndür
        return query.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val querySnapshot = task.result
                    querySnapshot?.let {
                        for (document in it.documents) {
                            val comment = document.toObject(Comment::class.java)
                            comment?.let {
                                commentsList.add(it)
                            }
                        }
                    }
                } else {
                    // Hata durumunu burada ele alabiliriz
                    // Örneğin: Loglama veya hata mesajı gösterme
                    task.exception?.let { e ->
                        println("getCommentsForPost Error: ${e.message}")
                    }
                }
            }
            .continueWith {
                commentsList
            }
    }

    fun createPost(post: Post): Task<Void> {
        // Yeni bir post oluştur
        val postRef = postsCollection.document()
        post.postId = postRef.id // Oluşturulan postun ID'sini al
        val setPostTask = postRef.set(post)

        // Yorum sayısını artırmak için async şekilde işlem yap

        // İki task'i birlikte çalıştır
        return Tasks.whenAll(setPostTask)
    }
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUser(userId: String): Task<User> {
        return usersCollection.document(userId)
            .get()
            .continueWith { task ->
                val document = task.result
                document?.toObject(User::class.java)
            }
    }



}
