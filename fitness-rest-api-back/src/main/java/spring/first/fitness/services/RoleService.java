package spring.first.fitness.services;


import spring.first.fitness.entity.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role);

    Role getRole(Long id);

    void deleteRole(Long id);

    List<Role> getAllRoles();
}
