package spring.first.fitness.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.first.fitness.dto.PostDTO;

import java.io.IOException;
import java.util.Map;


public interface PostService {

    PostDTO savePost(PostDTO role);

    Page<PostDTO> getAllPosts(Map<String, Object> filter, Pageable pageable);

    PostDTO getPost(Long id);

    void deletePost(Long id);

    void importPosts() throws IOException;

    void like(long postId, long userId);
}
