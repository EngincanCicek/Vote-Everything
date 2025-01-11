package com.example.voteeverything;

import android.util.Log;

import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PostGenerator {

    // Rastgele post ve comment oluşturma fonksiyonu todo burası sadece test için burası için güzel bir readme vs yaz
    public static List<Post> generateSamplePostsAndComments() {
        List<Post> samplePosts = new ArrayList<>();
        String[] titles = {
                "Arjantin Sağlık Şartları",
                "Example Şirketi",
                "AhmetVeli Ustanın Çorbası",
                "Dünya Teknoloji Zirvesi",
                "Yeşil Enerji Projeleri",
                "Eğitimde Dijitalleşme",
                "Uzay Araştırmaları",
                "Tarihsel Romanlar Kulübü",
                "Deniz Canlılarının Korunması",
                "Otomotiv Endüstrisi"
        };

        String[] descriptions = {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "Vivamus lacinia odio vitae vestibulum vestibulum.",
                "Curabitur eleifend congue nunc, vitae tincidunt metus.",
                "Donec a diam sit amet ligula dapibus aliquet.",
                "Aliquam erat volutpat. Nulla facilisi.",
                "Pellentesque sit amet nunc ut libero scelerisque sodales.",
                "Phasellus vel ex nec libero sodales pretium.",
                "Integer volutpat nunc nec arcu gravida venenatis.",
                "Nullam interdum, arcu at pharetra fringilla, libero lorem vehicula nisi.",
                "Suspendisse potenti. Praesent congue magna ut quam laoreet interdum."
        };

        String[] commentDescriptions = {
                "Çok güzel bir içerik.",
                "Katılıyorum.",
                "Bence daha iyi olabilir.",
                "Harika bir yazı olmuş.",
                "Teşekkürler, çok bilgilendiriciydi.",
                "Pek beğenmedim.",
                "Daha fazla detay beklerdim.",
                "Oldukça yararlı buldum.",
                "İçeriği güncelleyebilir misiniz?",
                "Emeğinize sağlık."
        };

        String[] userIds = {"user1", "user2"}; // Rastgele kullanıcılar

        Random random = new Random();

        for (int i = 0; i < titles.length; i++) {
            // Rastgele bir kullanıcı ata
            String userId = userIds[random.nextInt(userIds.length)];
            String postId = "post" + (i + 1);

            // Rastgele tarih
            String creationTime = String.valueOf(System.currentTimeMillis() - random.nextInt(10000000));
            String updateTime = String.valueOf(System.currentTimeMillis());

            // Post oluştur
            Post post = new Post(
                    postId,
                    descriptions[random.nextInt(descriptions.length)],
                    userId,
                    new ArrayList<>(),
                    creationTime,
                    updateTime
            );

            // Rastgele sayıda yorum oluştur (1-10 arası)
            int commentCount = 1 + random.nextInt(10);
            List<Comment> comments = new ArrayList<>();
            for (int j = 0; j < commentCount; j++) {
                String commentId = "comment" + (i + 1) + "_" + (j + 1);
                String commentUserId = userIds[random.nextInt(userIds.length)];
                int rating = 1 + random.nextInt(10);

                Comment comment = new Comment(
                        commentId,
                        postId,
                        commentUserId,
                        rating,
                        commentDescriptions[random.nextInt(commentDescriptions.length)],
                        creationTime,
                        updateTime
                );

                comments.add(comment);
            }

            // Yorumları posta ekle
            post.setComments(comments);

            // Postu listeye ekle
            samplePosts.add(post);
        }

        // Oluşturulan postları log ile yazdır
        for (Post post : samplePosts) {
            Log.d("PostGenerator", "Post: " + post.getPostId() + ", Description: " + post.getDescription());
            for (Comment comment : post.getComments()) {
                Log.d("PostGenerator", "  Comment: " + comment.getCommentId() + ", Rating: " + comment.getRating());
            }
        }

        return samplePosts;
    }
}
