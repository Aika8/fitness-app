package spring.first.fitness.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.first.fitness.dto.PasswordDTO;
import spring.first.fitness.dto.UserDTO;
import spring.first.fitness.security.oauth2.UserPrincipal;
import spring.first.fitness.security.oauth2.user.CurrentUser;
import spring.first.fitness.services.UserService;

@Slf4j
@RestController
@CrossOrigin
@Api(value = "Api for users")
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getCurrentUser(userPrincipal);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete user by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by ID")
    public ResponseEntity<UserDTO> getUser(@PathVariable final Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping()
    @ApiOperation(value = "Get user by email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam final String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    @ApiOperation(value = "update user's name, imageUrl or role")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    @PostMapping(value = "/password")
    @ApiOperation(value = "Update password")
    public ResponseEntity<?> updatePassword(@CurrentUser UserPrincipal userPrincipal, PasswordDTO passwordDTO) {
        userService.updatePassword(userPrincipal, passwordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "Get users")
    public ResponseEntity<Page<UserDTO>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
}
