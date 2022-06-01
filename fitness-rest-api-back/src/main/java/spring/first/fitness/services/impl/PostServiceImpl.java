package spring.first.fitness.services.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.first.fitness.dto.LikeUserDTO;
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.entity.ElasticPost;
import spring.first.fitness.entity.Post;
import spring.first.fitness.entity.Users;
import spring.first.fitness.exceptions.AccessDeniedException;
import spring.first.fitness.exceptions.BadRequestException;
import spring.first.fitness.exceptions.NotFoundException;
import spring.first.fitness.helpers.ElasticConstants;
import spring.first.fitness.helpers.ElasticHelper;
import spring.first.fitness.repos.ElasticPostRepository;
import spring.first.fitness.repos.PostRepository;
import spring.first.fitness.repos.UserRepository;
import spring.first.fitness.services.CommentService;
import spring.first.fitness.services.PostService;
import spring.first.fitness.util.DateUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ElasticPostRepository elasticPostRepository;

    private final Lock saveLock = new ReentrantLock(true);
    private final Lock deleteLock = new ReentrantLock(true);
    private static final String ID_NOT_FOUND = "No records with such an id: ";

    @Override
    @Transactional
    public PostDTO savePost(PostDTO post) {
        saveLock.lock();
        try {
            log.info("Post: {}", post);
            Post post1 = Post.builder()
                    .id(post.getId())
                    .access(post.getAccess())
                    .cover(post.getCover())
                    .priority(post.getPriority())
                    .dateOfCreation(post.getDateOfCreation() != null ? DateUtil.toDate(post.getDateOfCreation()) : LocalDateTime.now())
                    .build();
            post1 = postRepository.save(post1);
            log.info("Post id: {}", post1);

            ElasticPost elPost = ElasticPost.builder()
                    .postId(post1.getId())
                    .brief(post.getBrief())
                    .title(post.getTitle())
                    .description(post.getDescription())
                    .build();

            elasticPostRepository.save(elPost);

            elasticPostRepository.refresh();
            post.setId(post1.getId());
            return post;
        } finally {
            saveLock.unlock();
        }

    }

    @Override
    @Transactional
    public Page<PostDTO> getAllPosts(Map<String, Object> filter, Pageable pageable) {
        Page<PostDTO> dto;
        if (filter != null && !filter.isEmpty()) {
            log.info("Filter: {}", filter);
            NativeSearchQueryBuilder nativeSearchQueryBuilder = ElasticHelper.nativeQueryBuilder(filter, pageable);
            NativeSearchQuery query = nativeSearchQueryBuilder.build();
            log.info("Query: {}", query.getQuery());

            Page<ElasticPost> postModels = elasticPostRepository.search(query);
            dto = postModels.map(entity -> {
                Post post1 = postRepository.findById(entity.getPostId())
                        .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + entity.getPostId()));
                return PostDTO.builder()
                        .id(post1.getId())
                        .access(post1.getAccess())
                        .cover(post1.getCover())
                        .dateOfCreation(DateUtil.formatDateTime(post1.getDateOfCreation()))
                        .users(getlikeUsers(post1.getUsers()))
                        .priority(post1.getPriority())
                        .brief(entity.getBrief())
                        .title(entity.getTitle())
                        .build();
            });

        } else {
            dto = postRepository.findAllByOrderByPriorityAsc(pageable).map(post1 -> {
                log.info("Found post: {}", post1);
                ElasticPost entity = elasticPostRepository.findByPostId(post1.getId())
                        .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + post1.getId()));
                return PostDTO.builder()
                        .id(post1.getId())
                        .access(post1.getAccess())
                        .cover(post1.getCover())
                        .dateOfCreation(DateUtil.formatDateTime(post1.getDateOfCreation()))
                        .users(getlikeUsers(post1.getUsers()))
                        .priority(post1.getPriority())
                        .brief(entity.getBrief())
                        .title(entity.getTitle())
                        .build();
            });
        }
        log.info("data: " + dto);

        return dto;
    }


    String getIndex() {
        return ElasticConstants.POST_INDEX;
    }


    @Override
    @Transactional
    public PostDTO getPost(Long id) {
        AtomicReference<PostDTO> dto = new AtomicReference<>();
        boolean isAvailable;
        Post post1 = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            log.info(String.valueOf(auth.getPrincipal()));
            Optional<Users> user = userRepository.findByEmail(auth.getName());

            isAvailable = user.map(users -> {
                Integer weight = user.get().getRole().getWeight();
                return weight == 0 || weight >= post1.getAccess();
            }).orElseThrow(() -> new NotFoundException("not found user with username " + auth.getName()));

        } else {
            isAvailable = post1.getAccess() == 1;
        }

        log.info(String.valueOf(isAvailable));

        if (isAvailable) {
            ElasticPost elPost = elasticPostRepository.findByPostId(id)
                    .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + id));

            dto.set(PostDTO.builder()
                    .id(post1.getId())
                    .access(post1.getAccess())
                    .cover(post1.getCover())
                    .priority(post1.getPriority())
                    .dateOfCreation(DateUtil.formatDateTime(post1.getDateOfCreation()))
                    .users(getlikeUsers(post1.getUsers()))
                    .brief(elPost.getBrief())
                    .title(elPost.getTitle())
                    .description(elPost.getDescription())
                    .comments(commentService.readComment(post1.getId()))
                    .build());

            return dto.get();
        }

        throw new AccessDeniedException("access denied");
    }

    private Set<LikeUserDTO> getlikeUsers(Set<Users> users) {
        Set<LikeUserDTO> likeDtos = new HashSet<>();

        if (users != null) {
            users.forEach(user -> likeDtos.add(LikeUserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .imageUrl(user.getImageUrl()).build()));
        }
        return likeDtos;
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        deleteLock.lock();
        try {
            postRepository.deleteById(id);
            elasticPostRepository.deleteById(id);
        } finally {
            deleteLock.unlock();
        }
    }

    @Override
    @Transactional
    public void importPosts() throws IOException {
        if (!postRepository.findAll().isEmpty()) {
            throw new BadRequestException("database is not empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream stream = PostServiceImpl.class.getResourceAsStream("/post.json");
        String jsonTextElastic = IOUtils.toString(
                stream,
                String.valueOf(StandardCharsets.UTF_8));
        InputStream postStream = PostServiceImpl.class.getResourceAsStream("/post1.json");
        String jsonText = IOUtils.toString(
                postStream,
                String.valueOf(StandardCharsets.UTF_8));
        List<Post> posts = objectMapper.readValue(jsonText, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Post.class));
        List<ElasticPost> elasticPosts = objectMapper.readValue(jsonTextElastic, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, ElasticPost.class));

        if (!posts.isEmpty()) postRepository.saveAll(posts);
        for (int i = 0; i < posts.size(); i++) {
            elasticPosts.get(i).setPostId(posts.get(i).getId());
        }
        if (!elasticPosts.isEmpty()) elasticPostRepository.saveAll(elasticPosts);

    }

    @Override
    public void like(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + postId));
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ID_NOT_FOUND + userId));

        Set<Users> users = post.getUsers();
        if (users.contains(user)) {
            users.remove(user);
        } else {
            users.add(user);
        }

        postRepository.save(post);
    }


}
