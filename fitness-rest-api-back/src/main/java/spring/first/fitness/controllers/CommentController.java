package spring.first.fitness.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.first.fitness.payload.CommentRequest;
import spring.first.fitness.security.oauth2.UserPrincipal;
import spring.first.fitness.security.oauth2.user.CurrentUser;
import spring.first.fitness.services.CommentService;

import javax.annotation.security.RolesAllowed;
import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin
@Api(value = "Api for comments")
@RequestMapping(value = "/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/")
    @ApiOperation(value = "Write comment")
    public ResponseEntity<?> writeComment(@CurrentUser UserPrincipal userPrincipal, @RequestBody CommentRequest commentRequest) throws IOException {
        return ResponseEntity.ok(commentService.writeComment(commentRequest, userPrincipal.getId()));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete comment by id")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) {
        commentService.deleteComment(id, userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
