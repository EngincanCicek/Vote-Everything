package com.example.voteeverything;

import com.example.voteeverything.model.Post;

import java.util.Random;

public class StaticShorCut {
    public static String DEFAULTNAME = "ENGIN C";

    // Rastgele string üretmek için karakterlerin bulunduğu dizi
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String title = "Sample Title";
        String content = "Sample Content";

        // Unreachable statement hatası veren kod
        Post newPost = new Post(StaticShorCut.generateRandomString(8), StaticShorCut.generateRandomString(8), title, content, 0.0, null, 0);
        System.out.println(newPost);
    }


}
