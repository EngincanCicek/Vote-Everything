package com.example.voteeverything.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DummyPostCreatorForTest {

    public static List<Post> createDummyPosts() {
        List<Post> posts = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            User user = createDummyUser();


            Post post = new Post(
                    "Post " + (i + 1),
                    user.getUserId(),
                    "Post Başlığı " + (i + 1),
                    "Post İçeriği " + (i + 1),
                    i + 1,
                    new ArrayList<>()
            );

            Comment comment = createDummyComment(user);
            post.addComment(comment);

            posts.add(post);
        }

        return posts;
    }

    private static Comment createDummyComment(User user) {
        return new Comment(
                "Comment " + (new Random().nextInt(1000)),
                user.getUserId(),
                "Post " + (new Random().nextInt(5) + 1),
                "Bu bir dummy comment",
                new Random().nextDouble() * 5
        );
    }


    private static User createDummyUser() {
        return new User("idd","namee","email@.com");
    }
}
