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
import spring.first.fitness.dto.PostDTO;
import spring.first.fitness.dto.UserDTO;
import spring.first.fitness.entity.Role;
import spring.first.fitness.services.PostService;
import spring.first.fitness.services.RoleService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@Api(value = "Api for roles")
@RequestMapping(value = "/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get role by ID")
    public ResponseEntity<Role> getRole(@PathVariable final Long id) {
        return ResponseEntity.ok(roleService.getRole(id));
    }

    @PostMapping
    @ApiOperation(value = "Save role or if there id update role")
    public ResponseEntity<Role> save(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete role by id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "Get roles")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
