package spring.first.fitness.services;

import spring.first.fitness.entity.Comment;
import spring.first.fitness.payload.CommentRequest;
import spring.first.fitness.payload.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse writeComment(CommentRequest commentRequest, long userId);

    List<CommentResponse> readComment(long postId);

    void deleteComment(Long id, Long userId);

    void deleteAllComment(Long postId);
}
