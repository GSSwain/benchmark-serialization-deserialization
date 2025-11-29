package com.gsswain.benchmark.serialization.protobuf;

import com.gsswain.benchmark.serialization.model.pojo.SocialMediaPost;
import com.gsswain.benchmark.serialization.model.proto.Comment;
import com.gsswain.benchmark.serialization.model.proto.Media;
import java.util.stream.Collectors;

public class ProtobufMapper {

    public com.gsswain.benchmark.serialization.model.proto.SocialMediaPost map(SocialMediaPost pojoPost) {
        com.gsswain.benchmark.serialization.model.proto.SocialMediaPost.Builder postBuilder = com.gsswain.benchmark.serialization.model.proto.SocialMediaPost.newBuilder();
        postBuilder.setPostId(pojoPost.getPostId());
        postBuilder.setAuthorId(pojoPost.getAuthorId());
        postBuilder.setContent(pojoPost.getContent());
        postBuilder.setTimestamp(pojoPost.getTimestamp());
        if (pojoPost.getLikes() != null) {
            postBuilder.addAllLikes(pojoPost.getLikes());
        }
        if (pojoPost.getMediaAttachments() != null) {
            postBuilder.addAllMediaAttachments(pojoPost.getMediaAttachments().stream().map(this::map).collect(Collectors.toList()));
        }
        if (pojoPost.getComments() != null) {
            postBuilder.addAllComments(pojoPost.getComments().stream().map(this::map).collect(Collectors.toList()));
        }
        return postBuilder.build();
    }

    public Media map(SocialMediaPost.Media pojoMedia) {
        Media.Builder mediaBuilder = Media.newBuilder();
        mediaBuilder.setType(Media.MediaType.valueOf(pojoMedia.getType().name()));
        mediaBuilder.setUrl(pojoMedia.getUrl());
        return mediaBuilder.build();
    }

    public Comment map(SocialMediaPost.Comment pojoComment) {
        Comment.Builder commentBuilder = Comment.newBuilder();
        commentBuilder.setCommentId(pojoComment.getCommentId());
        commentBuilder.setCommenterId(pojoComment.getCommenterId());
        commentBuilder.setCommentText(pojoComment.getCommentText());
        commentBuilder.setTimestamp(pojoComment.getTimestamp());
        return commentBuilder.build();
    }
}