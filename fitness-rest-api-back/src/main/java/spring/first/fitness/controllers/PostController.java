package spring.first.fitness.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.security.oauth2.UserPrincipal;
import spring.first.fitness.security.oauth2.user.CurrentUser;
import spring.first.fitness.services.PostService;

@Slf4j
@RestController
@CrossOrigin
@Api(value = "Api for posts")
@RequestMapping(value = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get post by ID")
    public ResponseEntity<PostDTO> getPost(@PathVariable final Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping(value = "/filter")
    @ApiOperation(value = "Get posts")
    public ResponseEntity<Page<PostDTO>> getAll(
            @RequestBody(required = false) Map<String, Object> filter,
            @PageableDefault Pageable pageable) {
        log.info("Received filter: /api/post/filter ={}", filter);
        return ResponseEntity.ok(postService.getAllPosts(filter, pageable));
    }

    @PostMapping
    @ApiOperation(value = "Save post or if there id update post")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<PostDTO> save(@RequestBody PostDTO post) {
        return ResponseEntity.ok(postService.savePost(post));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete post by id")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/initialData")
    @RolesAllowed("ROLE_ADMIN")
    @ApiOperation(value = "Upload initial data to posts")
    public ResponseEntity<?> importPosts() throws IOException {
        postService.importPosts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/like")
    @ApiOperation(value = "Put like or remove like")
    public ResponseEntity<?> like(@RequestParam Long postId, @CurrentUser UserPrincipal userPrincipal) throws IOException {
        postService.like(postId, userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
