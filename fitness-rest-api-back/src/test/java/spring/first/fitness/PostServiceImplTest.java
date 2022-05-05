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
import spring.first.fitness.repos.ElasticPostRepository;
import spring.first.fitness.repos.PostRepository;
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

    @Test
    void testFindAllEmployees() {
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

}
