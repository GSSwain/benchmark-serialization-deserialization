package com.gsswain.benchmark.util;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class DataGenerator {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private DataGenerator() {
        // Private constructor to prevent instantiation
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(ThreadLocalRandom.current().nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public static int generateRandomId() {
        return ThreadLocalRandom.current().nextInt(1_000_001, 2_000_001);
    }

    public static SocialMediaPost generateSocialMediaPost() {
        SocialMediaPost post = new SocialMediaPost();
        post.setPostId(generateRandomString(10));
        post.setAuthorId(generateRandomString(10));
        post.setContent(generateRandomString(100));
        post.setTimestamp(System.currentTimeMillis());

        List<SocialMediaPost.Media> mediaAttachments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            SocialMediaPost.Media media = new SocialMediaPost.Media();
            media.setType(i % 2 == 0 ? SocialMediaPost.MediaType.IMAGE : SocialMediaPost.MediaType.VIDEO);
            media.setUrl("http://example.com/" + generateRandomString(15) + ".jpg");
            mediaAttachments.add(media);
        }
        post.setMediaAttachments(mediaAttachments);

        List<String> likes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            likes.add(generateRandomString(10));
        }
        post.setLikes(likes);

        List<SocialMediaPost.Comment> comments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SocialMediaPost.Comment comment = new SocialMediaPost.Comment();
            comment.setCommentId(generateRandomString(10));
            comment.setCommenterId(generateRandomString(10));
            comment.setCommentText(generateRandomString(50));
            comment.setTimestamp(System.currentTimeMillis());
            comments.add(comment);
        }
        post.setComments(comments);

        return post;
    }
}