package com.example.voteeverything.dao

import com.example.voteeverything.model.Comment
import com.example.voteeverything.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DummyDao {

    // Initialize Firebase Firestore
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Users") // Firestore koleksiyonu
    private val commentsCollection = db.collection("Comments") // Firestore koleksiyonu

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

    fun createComment(comment: Comment): Task<Void> {
        val document = commentsCollection.document() // Otomatik olarak yeni bir belge oluşturur
        comment.commentId = document.id // Yorum nesnesine otomatik atanan belge ID'sini set et
        return document.set(comment) // Firestore'daki belgeyi yükle
    }


    // Tüm yorumları getiren fonksiyon
    fun getAllComments(callback: (ArrayList<Comment>?, Exception?) -> Unit) {
        commentsCollection.get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                if (querySnapshot != null) {
                    val commentsList = ArrayList<Comment>()
                    for (document in querySnapshot.documents) {
                        val comment = document.toObject(Comment::class.java)
                        if (comment != null) {
                            commentsList.add(comment)
                        }
                    }
                    callback(commentsList, null)
                } else {
                    callback(null, Exception("QuerySnapshot is null"))
                }
            }
            .addOnFailureListener { e: Exception ->
                callback(null, e)
            }
    }


}
