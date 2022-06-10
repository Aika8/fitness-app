package spring.first.fitness.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.first.fitness.entity.Comment;
import spring.first.fitness.entity.Post;
import spring.first.fitness.entity.Users;
import spring.first.fitness.exceptions.AccessDeniedException;
import spring.first.fitness.exceptions.NotFoundException;
import spring.first.fitness.payload.CommentRequest;
import spring.first.fitness.payload.CommentResponse;
import spring.first.fitness.repos.CommentRepository;
import spring.first.fitness.repos.PostRepository;
import spring.first.fitness.repos.UserRepository;
import spring.first.fitness.services.CommentService;
import spring.first.fitness.util.DateUtil;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private static final String ID_NOT_FOUND = "No records with such an id: ";

    @Override
    public CommentResponse writeComment(CommentRequest commentRequest, long userId) {
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + commentRequest.getPostId()));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + userId));

        Comment comment = Comment.builder()
                .id(commentRequest.getId())
                .creationDate(LocalDateTime.now())
                .message(commentRequest.getMessage())
                .post(post)
                .user(user)
                .build();

        Comment save = commentRepository.save(comment);

        return CommentResponse.builder()
            .id(save.getId())
            .message(save.getMessage())
            .creationDate(DateUtil.formatDateTime(comment.getCreationDate()))
            .userEmail(save.getUser().getEmail())
            .userImg(save.getUser().getImageUrl()).build();
    }

    @Override
    public List<CommentResponse> readComment(long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment: comments) {
            responses.add(CommentResponse.builder()
                    .id(comment.getId())
                    .userEmail(comment.getUser().getEmail())
                    .creationDate(DateUtil.formatDateTime(comment.getCreationDate()))
                    .message(comment.getMessage())
                    .userImg(comment.getUser().getImageUrl())
                    .build());
        }

        return responses;

    }

    @Override
    public void deleteComment(Long id, Long userId) {

        Comment comment = commentRepository.findById(userId).orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + id));

        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("The comment is not yours");
        }

        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllComment(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        commentRepository.deleteAll(comments);
    }


}
