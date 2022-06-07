package spring.first.fitness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.entity.ElasticPost;
import spring.first.fitness.entity.Post;
import spring.first.fitness.exceptions.AccessDeniedException;
import spring.first.fitness.payload.CommentResponse;
import spring.first.fitness.repos.ElasticPostRepository;
import spring.first.fitness.repos.PostRepository;
import spring.first.fitness.services.CommentService;
import spring.first.fitness.services.impl.PostServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    PostServiceImpl service;

    @Mock
    PostRepository postRepository;

    @Mock
    ElasticPostRepository elasticPostRepository;

    @Mock
    CommentService commentService;

    @Test
    void testFindAllPosts() {
        List<Post> list = new ArrayList<>();

        list.add(Post.builder().build());
        list.add(Post.builder().build());
        list.add(Post.builder().build());

        when(postRepository.findAllByOrderByPriorityAsc( Pageable.unpaged())).thenReturn(new PageImpl<>(list));
        when(elasticPostRepository.findByPostId(any())).thenReturn(Optional.of(ElasticPost.builder().build()));

        //test
        Page<PostDTO> empList = service.getAllPosts(null, Pageable.unpaged());

        Assertions.assertEquals(3, empList.getTotalElements());
        verify(postRepository, times(1)).findAllByOrderByPriorityAsc(Pageable.unpaged());
    }

    @Test
    void testGetPostThrowsAccessDenied() {

        when(postRepository.findById(any())).thenReturn(Optional.of(Post.builder().access(2).build()));

        //test
        Exception exception = Assertions.assertThrows(AccessDeniedException.class, () -> {
            service.getPost(3L);
        });
        verify(elasticPostRepository, times(0)).findByPostId(3L);
        Assertions.assertEquals("access denied", exception.getMessage());
    }


    @Test
    void testGetPost() {
        when(postRepository.findById(any())).thenReturn(Optional.of(Post.builder().id(3L).access(1).build()));
        when(elasticPostRepository.findByPostId(any())).thenReturn(Optional.of(ElasticPost.builder().build()));

        //test
        PostDTO post = service.getPost(3L);

        Assertions.assertEquals(1, post.getAccess());
        verify(elasticPostRepository, times(1)).findByPostId(3L);
        verify(postRepository, times(1)).findById(3L);
    }
}
