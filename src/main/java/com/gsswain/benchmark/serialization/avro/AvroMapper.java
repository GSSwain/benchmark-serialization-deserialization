package com.gsswain.benchmark.serialization.avro;

import com.gsswain.benchmark.serialization.model.avro.Comment;
import com.gsswain.benchmark.serialization.model.avro.Media;
import com.gsswain.benchmark.serialization.model.avro.MediaType;
import com.gsswain.benchmark.serialization.model.avro.SocialMediaPost;
import java.util.Collections;
import java.util.stream.Collectors;

public class AvroMapper {

    public SocialMediaPost map(com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost pojoPost) {
        SocialMediaPost avroPost = new SocialMediaPost();
        avroPost.setPostId(pojoPost.getPostId());
        avroPost.setAuthorId(pojoPost.getAuthorId());
        avroPost.setContent(pojoPost.getContent());
        avroPost.setTimestamp(pojoPost.getTimestamp());
        avroPost.setLikes(pojoPost.getLikes() != null ? pojoPost.getLikes() : Collections.emptyList());

        if (pojoPost.getMediaAttachments() != null) {
            avroPost.setMediaAttachments(pojoPost.getMediaAttachments().stream().map(this::map).collect(Collectors.toList()));
        } else {
            avroPost.setMediaAttachments(Collections.emptyList());
        }

        if (pojoPost.getComments() != null) {
            avroPost.setComments(pojoPost.getComments().stream().map(this::map).collect(Collectors.toList()));
        } else {
            avroPost.setComments(Collections.emptyList());
        }
        return avroPost;
    }

    public Media map(com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost.Media pojoMedia) {
        Media avroMedia = new Media();
        avroMedia.setUrl(pojoMedia.getUrl());
        avroMedia.setType(MediaType.valueOf(pojoMedia.getType().name()));
        return avroMedia;
    }

    public Comment map(com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost.Comment pojoComment) {
        Comment avroComment = new Comment();
        avroComment.setCommentId(pojoComment.getCommentId());
        avroComment.setCommenterId(pojoComment.getCommenterId());
        avroComment.setCommentText(pojoComment.getCommentText());
        avroComment.setTimestamp(pojoComment.getTimestamp());
        return avroComment;
    }
}