package com.rev_connect_api.services;

import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.repositories.CommentLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentLikesService {
    @Autowired
    private CommentLikesRepository commentLikesRepository;


    public void like(CommentLikes like) {
        // Check if the user already liked the comment or post
        Optional<CommentLikes> existingLike = commentLikesRepository.findByUserIdAndCommentId(
                like.getUserId(), like.getCommentId());
        if (!existingLike.isPresent()) {
            commentLikesRepository.save(like);
        }
    }

    public void unlike(long userId, Long commentId) {
        Optional<CommentLikes> existingLike = commentLikesRepository.findByUserIdAndCommentId(
                userId, commentId);
        existingLike.ifPresent(commentLikesRepository::delete);
    }

    public long countLikesForComment(long commentId) {
        return commentLikesRepository.countByCommentId(commentId);
    }
}
